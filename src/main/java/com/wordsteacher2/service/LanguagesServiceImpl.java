package com.wordsteacher2.service;

import com.wordsteacher2.dto.LanguageDto;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.model.Language;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.User;
import com.wordsteacher2.repository.*;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguagesServiceImpl implements LanguagesService {
    private final LanguagesRepository languagesRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final WordsRepository wordsRepository;
    private final DictionaryRepository dictionaryRepository;
    private final UsersRepository usersRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public LanguagesServiceImpl(LanguagesRepository languagesRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, WordsRepository wordsRepository, DictionaryRepository dictionaryRepository, UsersRepository usersRepository, ModelConverter modelConverter) {
        this.languagesRepository = languagesRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.wordsRepository = wordsRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.usersRepository = usersRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<LanguageDto> getUserLanguages(Integer userId) {
        return modelConverter.convertLanguagesToDtoList(languagesRepository.findAllByUserId(userId));
    }

    @Override
    public Integer getLanguageId(String language, Integer userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPlan().equals("ultimate")) {
                return languagesRepository.findByLanguageAndUserId(language, userId).getId();
            }
        }
        throw new NoPermissionException();
    }

    @Override
    public List<LanguageDto> addLanguage(LanguageDto languageDto) {
        Optional<User> userOptional = usersRepository.findById(languageDto.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPlan().equals("ultimate")) {
                languagesRepository.save(modelConverter.convert(languageDto));
                Integer languageId = languagesRepository.findByLanguageAndUserId(languageDto.getLanguage(), languageDto.getUserId()).getId();
                levelRepository.save(new Level(1, languageDto.getUserId(), languageId));
                statisticsRepository.save(new Statistic(0, 0, 0, languageDto.getUserId(), languageId));
                return modelConverter.convertLanguagesToDtoList(languagesRepository.findAllByUserId(languageDto.getUserId()));
            }
        }
        throw new NoPermissionException();
    }

    @Override
    public List<LanguageDto> addFirstLanguage(LanguageDto languageDto) {
        Optional<User> userOptional = usersRepository.findById(languageDto.getUserId());
        if (userOptional.isPresent() && userOptional.get().getPlan().equals("Free") && languagesRepository.findFirstByUserId(languageDto.getUserId()) == null) {
            languagesRepository.save(modelConverter.convert(languageDto));
            Integer languageId = languagesRepository.findByLanguageAndUserId(languageDto.getLanguage(), languageDto.getUserId()).getId();
            levelRepository.save(new Level(1, languageDto.getUserId(), languageId));
            statisticsRepository.save(new Statistic(0, 0, 0, languageDto.getUserId(), languageId));
            return modelConverter.convertLanguagesToDtoList(languagesRepository.findAllByUserId(languageDto.getUserId()));
        }
        throw new NoPermissionException();
    }

    @Override
    public List<LanguageDto> removeLanguage(String language, Integer userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPlan().equals("ultimate") && languagesRepository.findByLanguageAndUserId(language, userId) != null) {
                Integer languageId = languagesRepository.findByLanguageAndUserId(language, userId).getId();
                levelRepository.deleteByUserIdAndLanguageId(userId, languageId);
                statisticsRepository.deleteByUserIdAndLanguageId(userId, languageId);
                wordsRepository.deleteAllByUserIdAndLanguageId(userId, languageId);
                dictionaryRepository.deleteAllByUserIdAndLanguageId(userId, languageId);
                return modelConverter.convertLanguagesToDtoList(languagesRepository.deleteByLanguageAndUserId(language, userId));
            }
        }
        throw new NoPermissionException();
    }
}
