package com.wordsteacher2.service;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.dto.DictionaryListWithAdvancementDto;
import com.wordsteacher2.model.Dictionary;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.repository.DictionaryRepository;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final StatisticsRepository statisticsRepository;
    private final ModelConverter modelConverter;
    private final StatisticsService statisticsService;
    private Advancement advancement;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, StatisticsRepository statisticsRepository, ModelConverter modelConverter, StatisticsService statisticsService) {
        this.dictionaryRepository = dictionaryRepository;
        this.statisticsRepository = statisticsRepository;
        this.modelConverter = modelConverter;
        this.statisticsService = statisticsService;
    }

    @Override
    public List<DictionaryDto> getWords(String type, Integer userId, Integer languageId) {
        List<Dictionary> words = dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(userId, languageId);

        if ("word".equals(type)) {
            Iterator<Dictionary> iterator = words.iterator();

            while (iterator.hasNext()) {
                Dictionary dictionary = iterator.next();
                String word = dictionary.getWord();

                if (word.trim().split("\\s+").length > 1 && Arrays.stream(new String[]{"Der", "Die", "Das", "Sich"}).noneMatch(word::startsWith)) {
                    iterator.remove();
                }
            }
        }
        return modelConverter.convertDictionaryToDtoList(words);
    }


    @Override
    public DictionaryListWithAdvancementDto addWord(DictionaryDto dictionaryDto) {
        Dictionary existedWord = dictionaryRepository.findByWordAndMeaningAndUserIdAndLanguageId(dictionaryDto.getWord(), dictionaryDto.getMeaning(), dictionaryDto.getUserId(), dictionaryDto.getLanguageId());
        if (existedWord == null) {
            dictionaryRepository.save(modelConverter.convert(dictionaryDto));
        }

        Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(dictionaryDto.getUserId(), dictionaryDto.getLanguageId());
        assert statistic != null;
        statistic.setWordsLearned(statistic.getWordsLearned() + 1);
        statisticsRepository.save(statistic);
        this.advancement = statisticsService.getLearnedWordsAdvancement();


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

        dictionaryRepository.save(dictionary);
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetterAndByUserIdAndLanguageId(userId, languageId));
    }
}
