package com.wordsteacher2.service;

import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final ModelConverter modelConverter;
    private Advancement advancement;

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

        switch (wordsLearned) {
            case 5000:
                return Advancement.FIVETHOUSANDWORDS;
            case 1000:
                return Advancement.THOUSANDWORDS;
            case 500:
                return Advancement.FIVEHUNDREDWORDS;
            case 100:
                return Advancement.HUNDREDWORDS;
            case 50:
                return Advancement.FIFTYWORDS;
            case 10:
                return Advancement.TENWORDS;
            default:
                return null;
        }
    }

    @Override
    public Advancement getCyclesAdvancement(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);

        int cycles = statistic.getCycles();

        switch (cycles) {
            case 50:
                return Advancement.FIFTYCYCLESSTREAK;
            case 20:
                return Advancement.TWENTYCYCLESSTREAK;
            case 10:
                return Advancement.TENCYCLESSTREAK;
            case 5:
                return Advancement.FIVECYCLESSTREAK;
            case 1:
                return Advancement.ONECYCLESTREAK;
            default:
                return null;
        }
    }


    @Override
    public Advancement getDayStreakAdvancement(Integer userId, Integer languageId) {
        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);

        int dayStreak = statistic.getDayStreak();

        switch (dayStreak) {
            case 365:
                return Advancement.ONEYEARSTREAK;
            case 180:
                return Advancement.SIXMONTHSTREAK;
            case 60:
                return Advancement.TWOMONTHSTREAK;
            case 30:
                return Advancement.MONTHSTREAK;
            case 21:
                return Advancement.THREEWEEKSTREAK; // Corrected typo: TREEWEEKSTREAK -> THREEWEEKSTREAK
            case 14:
                return Advancement.TWOWEEKSTREAK;
            case 7:
                return Advancement.WEEKSTREAK;
            default:
                return null;
        }
    }
}
