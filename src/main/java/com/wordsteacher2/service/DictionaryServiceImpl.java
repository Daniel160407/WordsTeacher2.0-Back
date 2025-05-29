package com.wordsteacher2.service;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.dto.DictionaryListWithAdvancementDto;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.model.Dictionary;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.User;
import com.wordsteacher2.repository.DictionaryRepository;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.repository.UsersRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final StatisticsRepository statisticsRepository;
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;
    private final StatisticsService statisticsService;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, StatisticsRepository statisticsRepository, UsersRepository usersRepository, ModelConverter modelConverter, StatisticsService statisticsService) {
        this.dictionaryRepository = dictionaryRepository;
        this.statisticsRepository = statisticsRepository;
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
        this.statisticsService = statisticsService;
    }

    @Override
    public DictionaryListWithAdvancementDto getWords(String type, Integer userId, Integer languageId, Boolean tests) {
        List<Dictionary> words = dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(userId, languageId);

        if ("word".equals(type)) {
            Iterator<Dictionary> iterator = words.iterator();
            while (iterator.hasNext()) {
                Dictionary dictionary = iterator.next();
                String word = dictionary.getWord();

                if (word.trim().split("\\s+").length > 1 &&
                        Arrays.stream(new String[]{"Der", "Die", "Das", "Sich"}).noneMatch(word::startsWith)) {
                    iterator.remove();
                }
            }
        }

        if (tests) {
            Optional<User> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent() && !userOptional.get().getPlan().equals("free")) {
                return modelConverter.convertDict(words, "");
            } else {
                throw new NoPermissionException();
            }
        }

        Advancement advancement = updateDayStreakDate(userId, languageId);
        return modelConverter.convertDict(words, advancement != null ? advancement.getDescription() : "");
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
                    advancement = statisticsService.getDayStreakAdvancement(userId, languageId);
                    statistic.setLastActivityDate(today.format(dateFormatter));
                    statistic.setDayStreak(statistic.getDayStreak() + 1);
                } else if (lastActivityDate.isBefore(today.minusDays(1))) {
                    statistic.setLastActivityDate(today.format(dateFormatter));
                    statistic.setDayStreak(1);
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

    @Override
    public DictionaryListWithAdvancementDto addWord(DictionaryDto dictionaryDto) {
        Advancement advancement = null;
        Dictionary existedWord = dictionaryRepository.findByWordAndMeaningAndUserIdAndLanguageId(dictionaryDto.getWord(), dictionaryDto.getMeaning(), dictionaryDto.getUserId(), dictionaryDto.getLanguageId());
        if (existedWord == null) {
            dictionaryRepository.save(modelConverter.convert(dictionaryDto));
        }

        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(dictionaryDto.getUserId(), dictionaryDto.getLanguageId());
        assert statistic != null;
        statistic.setWordsLearned(statistic.getWordsLearned() + 1);
        statisticsRepository.save(statistic);
        advancement = statisticsService.getLearnedWordsAdvancement(dictionaryDto.getUserId(), dictionaryDto.getLanguageId());


        return modelConverter.convertDict(dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(dictionaryDto.getUserId(), dictionaryDto.getLanguageId()), advancement != null ? advancement.getDescription() : null);
    }

    @Override
    public List<DictionaryDto> deleteWord(DictionaryDto dictionaryDto) {
        dictionaryRepository.deleteByWordAndMeaningAndUserIdAndLanguageId(dictionaryDto.getWord(), dictionaryDto.getMeaning(), dictionaryDto.getUserId(), dictionaryDto.getLanguageId());

        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(dictionaryDto.getUserId(), dictionaryDto.getLanguageId());
        assert statistic != null;
        statistic.setWordsLearned(statistic.getWordsLearned() - 1);
        statisticsRepository.save(statistic);

        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(dictionaryDto.getUserId(), dictionaryDto.getLanguageId()));
    }

    @Override
    public List<DictionaryDto> editWord(List<DictionaryDto> dictionaryDtos) {
        DictionaryDto original = dictionaryDtos.get(0);
        DictionaryDto changed = dictionaryDtos.get(1);
        Integer userId = original.getUserId();
        Integer languageId = original.getLanguageId();

        Dictionary dictionary = dictionaryRepository.findByWordAndMeaningAndUserIdAndLanguageId(original.getWord(), original.getMeaning(), userId, languageId);
        dictionary.setWord(changed.getWord());
        dictionary.setMeaning(changed.getMeaning());
        dictionary.setLevel(changed.getLevel());

        dictionaryRepository.save(dictionary);
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(userId, languageId));
    }
}
