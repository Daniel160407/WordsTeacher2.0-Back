package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.dto.WordListWithAdvancementDto;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.User;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.LevelRepository;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.repository.UsersRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import lombok.Getter;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordDropperServiceImpl implements WordDropperService {
    private final WordsRepository wordsRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final UsersRepository usersRepository;
    private final StatisticsService statisticsService;
    private final ModelConverter modelConverter;
    @Getter
    private Advancement advancement;

    @Autowired
    public WordDropperServiceImpl(WordsRepository wordsRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, UsersRepository usersRepository, StatisticsService statisticsService, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.usersRepository = usersRepository;
        this.statisticsService = statisticsService;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getDroppedWords(Integer userId, Integer languageId, Boolean tests) {
        if (tests) {
            Optional<User> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent() && !userOptional.get().getPlan().equals("free")) {
                return modelConverter.convertWordsToDtoList(
                        wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "false", userId, languageId));
            } else {
                throw new NoPermissionException();
            }
        }
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "false", userId, languageId));
    }

    @Override
    public WordListWithAdvancementDto dropWords(List<WordDto> wordDtos) {
        Integer userId = wordDtos.get(0).getUserId();
        Integer languageId = wordDtos.get(0).getLanguageId();
        for (WordDto wordDto : wordDtos) {
            Word word = wordsRepository.findByWordAndMeaningAndUserIdAndLanguageId(wordDto.getWord(), wordDto.getMeaning(), userId, languageId);
            word.setActive("false");
            wordsRepository.save(word);
        }

        if (wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "true", userId, languageId).isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "false", userId, languageId);
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }

            Level level = levelRepository.findByUserIdAndLanguageId(userId, languageId);
            assert level != null;

            if (level.getLevel() >= 5) {
                level.setLevel(1);
                wordsRepository.deleteAllByWordTypeAndUserIdAndLanguageId("word", userId, languageId);

                Statistic statistic = statisticsRepository.findByUserIdAndLanguageId(userId, languageId);
                statistic.setCycles(statistic.getCycles() + 1);
                statisticsRepository.save(statistic);
                this.advancement = statisticsService.getCyclesAdvancement(userId, languageId);
            } else {
                level.setLevel(level.getLevel() + 1);
            }

            levelRepository.save(level);
        } else if (wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("difficult", "true", userId, languageId).isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("difficult", "false", userId, languageId);
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }
        }

        if (!wordDtos.isEmpty() && "difficult".equals(wordDtos.get(0).getWordType())) {
            return modelConverter.convert(wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("difficult", "true", userId, languageId),
                    advancement != null ? advancement.getDescription() : null);
        }
        return modelConverter.convert(wordsRepository.findAllByWordTypeAndActiveAndUserIdAndLanguageId("word", "true", userId, languageId), advancement != null ? advancement.getDescription() : null);
    }
}