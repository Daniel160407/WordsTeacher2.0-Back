package com.wordsteacher2.service;

import com.wordsteacher2.dto.LanguageDto;
import com.wordsteacher2.model.Language;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.repository.*;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguagesServiceImpl implements LanguagesService {
    private final LanguagesRepository languagesRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final WordsRepository wordsRepository;
    private final DictionaryRepository dictionaryRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public LanguagesServiceImpl(LanguagesRepository languagesRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, WordsRepository wordsRepository, DictionaryRepository dictionaryRepository, ModelConverter modelConverter) {
        this.languagesRepository = languagesRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.wordsRepository = wordsRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<LanguageDto> getUserLanguages(Integer userId) {
        return modelConverter.convertLanguagesToDtoList(languagesRepository.findAllByUserId(userId));
    }

    @Override
    public Integer getLanguageId(String language, Integer userId) {
        return languagesRepository.findByLanguageAndUserId(language, userId).getId();
    }

    @Override
    public List<LanguageDto> addLanguage(LanguageDto languageDto) {
        languagesRepository.save(modelConverter.convert(languageDto));
        Integer languageId = languagesRepository.findByLanguageAndUserId(languageDto.getLanguage(), languageDto.getUserId()).getId();
        levelRepository.save(new Level(1, languageDto.getUserId(), languageId));
        statisticsRepository.save(new Statistic(0, 0, 0, languageDto.getUserId(), languageId));
        return modelConverter.convertLanguagesToDtoList(languagesRepository.findAllByUserId(languageDto.getUserId()));
    }

    @Override
    public List<LanguageDto> removeLanguage(String language, Integer userId) {
        Integer languageId = languagesRepository.findByLanguageAndUserId(language, userId).getId();
        levelRepository.deleteByUserIdAndLanguageId(userId, languageId);
        statisticsRepository.deleteByUserIdAndLanguageId(userId, languageId);
        wordsRepository.deleteAllByUserIdAndLanguageId(userId, languageId);
        dictionaryRepository.deleteAllByUserIdAndLanguageId(userId, languageId);
        return modelConverter.convertLanguagesToDtoList(languagesRepository.deleteByLanguageAndUserId(language, userId));
    }
}
