package com.wordsteacher2.service;

import com.wordsteacher2.dto.StatisticDto;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, ModelConverter modelConverter) {
        this.statisticsRepository = statisticsRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Advancement checkStatistics() {
        return null;
    }

    @Override
    public Advancement getLearnedWordsAdvancement(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
        int wordsLearned = statistic.getWordsLearned();

        Advancement advancement = switch (wordsLearned) {
            case 5000 -> Advancement.FIVETHOUSANDWORDS;
            case 3000 -> Advancement.THREETHOUSANDWORDS;
            case 1000 -> Advancement.THOUSANDWORDS;
            case 700 -> Advancement.SEVENHUNDREDWORDS;
            case 500 -> Advancement.FIVEHUNDREDWORDS;
            case 300 -> Advancement.THREEHUNDREDWORDS;
            case 100 -> Advancement.HUNDREDWORDS;
            case 50 -> Advancement.FIFTYWORDS;
            case 10 -> Advancement.TENWORDS;
            default -> null;
        };

        return applyAdvancement(statistic, advancement);
    }

    @Override
    public Advancement getCyclesAdvancement(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
        int cycles = statistic.getCycles();

        Advancement advancement = switch (cycles) {
            case 50 -> Advancement.FIFTYCYCLESSTREAK;
            case 30 -> Advancement.THIRTYCYCLESSTREAK;
            case 20 -> Advancement.TWENTYCYCLESSTREAK;
            case 10 -> Advancement.TENCYCLESSTREAK;
            case 5 -> Advancement.FIVECYCLESSTREAK;
            case 1 -> Advancement.ONECYCLESTREAK;
            default -> null;
        };

        return applyAdvancement(statistic, advancement);
    }

    @Override
    public Advancement getDayStreakAdvancement(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
        int dayStreak = statistic.getDayStreak() + 1;

        Advancement advancement = switch (dayStreak) {
            case 365 -> Advancement.ONEYEARSTREAK;
            case 180 -> Advancement.SIXMONTHSTREAK;
            case 60 -> Advancement.TWOMONTHSTREAK;
            case 30 -> Advancement.MONTHSTREAK;
            case 21 -> Advancement.THREEWEEKSTREAK;
            case 14 -> Advancement.TWOWEEKSTREAK;
            case 7 -> Advancement.WEEKSTREAK;
            case 3 -> Advancement.THREEDAYSTREAK;
            case 1 -> Advancement.ONEDAYSTREAK;
            default -> null;
        };

        return applyAdvancement(statistic, advancement);
    }

    @Override
    public StatisticDto getStatistics(Integer userId, Integer languageId) {
        Advancement advancement = updateDayStreakDate(userId, languageId);
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
        return modelConverter.convert(statistic, advancement != null ? advancement.getDescription() : "");
    }

    @Override
    public List<String> getAdvancements() {
        return Arrays.stream(Advancement.values())
                .map(Advancement::getDescription)
                .collect(Collectors.toList());
    }

    private Advancement applyAdvancement(Statistic statistic, Advancement advancement) {
        if (advancement == null) return null;

        String existing = statistic.getAdvancements();
        String newAdvancement = advancement.getDescription();

        boolean alreadyHasIt = existing != null && existing.contains(newAdvancement);
        if (alreadyHasIt) return null;

        String updated = StringUtils.hasText(existing)
                ? existing + ", " + newAdvancement
                : newAdvancement;

        statistic.setAdvancements(updated);
        statisticsRepository.save(statistic);

        return advancement;
    }

    private Advancement updateDayStreakDate(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
        Advancement advancement = null;
        if (statistic != null && statistic.getLastActivityDate() != null && !statistic.getLastActivityDate().isEmpty()) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate lastActivityDate = LocalDate.parse(statistic.getLastActivityDate(), dateFormatter);
                LocalDate today = LocalDate.now();
                if (lastActivityDate.isEqual(today.minusDays(1))) {
                    advancement = getDayStreakAdvancement(userId, languageId);
                    statistic.setLastActivityDate(today.format(dateFormatter));
                    statistic.setDayStreak(statistic.getDayStreak() + 1);
                } else if (lastActivityDate.isBefore(today.minusDays(1))) {
                    statistic.setLastActivityDate(today.format(dateFormatter));
                    statistic.setDayStreak(0);
                    statisticsRepository.save(statistic);
                }
            } catch (DateTimeException ignored) {
            }
        } else if (statistic.getLastActivityDate().isEmpty()) {
            statistic.setLastActivityDate(LocalDate.now().toString());
        }

        statisticsRepository.save(statistic);
        return advancement;
    }
}
