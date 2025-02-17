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
    public List<DictionaryDto> getWords(String type) {
        List<Dictionary> words = dictionaryRepository.findAllSortedByFirstLetter();

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
        Dictionary existedWord = dictionaryRepository.findByWordAndMeaning(dictionaryDto.getWord(), dictionaryDto.getMeaning());
        if (existedWord == null) {
            dictionaryRepository.save(modelConverter.convert(dictionaryDto));
        }

        Statistic statistic = statisticsRepository.findById(1).orElse(null);
        assert statistic != null;
        statistic.setWordsLearned(statistic.getWordsLearned() + 1);
        statisticsRepository.save(statistic);
        this.advancement = statisticsService.getLearnedWordsAdvancement();


        return modelConverter.convertDict(dictionaryRepository.findAllSortedByFirstLetter(), advancement != null ? advancement.getDescription() : null);
    }

    @Override
    public List<DictionaryDto> deleteWord(DictionaryDto dictionaryDto) {
        dictionaryRepository.deleteByWordAndMeaning(dictionaryDto.getWord(), dictionaryDto.getMeaning());

        Statistic statistic = statisticsRepository.findById(1).orElse(null);
        assert statistic != null;
        statistic.setWordsLearned(statistic.getWordsLearned() - 1);
        statisticsRepository.save(statistic);

        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }

    @Override
    public List<DictionaryDto> editWord(List<DictionaryDto> dictionaryDtos) {
        DictionaryDto original = dictionaryDtos.get(0);
        DictionaryDto changed = dictionaryDtos.get(1);

        Dictionary dictionary = dictionaryRepository.findByWordAndMeaning(original.getWord(), original.getMeaning());
        dictionary.setWord(changed.getWord());
        dictionary.setMeaning(changed.getMeaning());

        dictionaryRepository.save(dictionary);
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }
}
