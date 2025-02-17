package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.dto.WordListWithAdvancementDto;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.LevelRepository;
import com.wordsteacher2.repository.StatisticsRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.service.advancement.Advancement;
import com.wordsteacher2.util.ModelConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordDropperServiceImpl implements WordDropperService {
    private final WordsRepository wordsRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final StatisticsService statisticsService;
    private final ModelConverter modelConverter;
    @Getter
    private Advancement advancement;

    @Autowired
    public WordDropperServiceImpl(WordsRepository wordsRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, StatisticsService statisticsService, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.statisticsService = statisticsService;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getDroppedWords(Integer userId) {
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserId("word", "false", userId));
    }

    @Override
    public WordListWithAdvancementDto dropWords(List<WordDto> wordDtos) {
        Integer userId = wordDtos.get(0).getUserId();
        for (WordDto wordDto : wordDtos) {
            Word word = wordsRepository.findByWordAndMeaningAndUserId(wordDto.getWord(), wordDto.getMeaning(), userId);
            word.setActive("false");
            wordsRepository.save(word);
        }

        if (wordsRepository.findAllByWordTypeAndActiveAndUserId("word", "true", userId).isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActiveAndUserId("word", "false", userId);
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }

            Level level = levelRepository.findByUserId(userId);
            assert level != null;

            if (level.getLevel() >= 5) {
                level.setLevel(1);
                wordsRepository.deleteAllByWordTypeAndUserId("word",userId);

                Statistic statistic = statisticsRepository.findByUserId(userId);
                statistic.setCycles(statistic.getCycles() + 1);
                statisticsRepository.save(statistic);
                this.advancement = statisticsService.getCyclesAdvancement();
            } else {
                level.setLevel(level.getLevel() + 1);
            }

            levelRepository.save(level);
        } else if (wordsRepository.findAllByWordTypeAndActiveAndUserId("difficult", "true", userId).isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActiveAndUserId("difficult", "false", userId);
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }
        }

        if (!wordDtos.isEmpty() && "difficult".equals(wordDtos.get(0).getWordType())) {
            return modelConverter.convert(wordsRepository.findAllByWordTypeAndActiveAndUserId("difficult", "true", userId),
                    advancement != null ? advancement.getDescription() : null);
        }
        return modelConverter.convert(wordsRepository.findAllByWordTypeAndActiveAndUserId("word", "true", userId), advancement != null ? advancement.getDescription() : null);
    }
}