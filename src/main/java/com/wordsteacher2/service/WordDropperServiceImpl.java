package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.dto.WordListWithAdvancementDto;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Statistic;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.DroppedWordsRepository;
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
    private final DroppedWordsRepository droppedWordsRepository;
    private final LevelRepository levelRepository;
    private final StatisticsRepository statisticsRepository;
    private final StatisticsService statisticsService;
    private final ModelConverter modelConverter;
    @Getter
    private Advancement advancement;

    @Autowired
    public WordDropperServiceImpl(WordsRepository wordsRepository, DroppedWordsRepository droppedWordsRepository, LevelRepository levelRepository, StatisticsRepository statisticsRepository, StatisticsService statisticsService, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.droppedWordsRepository = droppedWordsRepository;
        this.levelRepository = levelRepository;
        this.statisticsRepository = statisticsRepository;
        this.statisticsService = statisticsService;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getDroppedWords() {
        return modelConverter.convertDroppedWordsToDtoList(droppedWordsRepository.findAll());
    }

    @Override
    public WordListWithAdvancementDto dropWords(List<WordDto> wordDtos) {
        for (WordDto wordDto : wordDtos) {
            wordsRepository.deleteByWordAndMeaning(wordDto.getWord(), wordDto.getMeaning());
        }
        droppedWordsRepository.saveAll(modelConverter.convertDtoToDroppedWordsList(wordDtos));
        if (wordsRepository.findAllByWordType("word").isEmpty() && !droppedWordsRepository.findAllByWordType("word").isEmpty()) {
            List<Word> droppedWords = modelConverter.convertDroppedWordsToWordsList(droppedWordsRepository.findAllByWordType("word"));
            wordsRepository.saveAll(droppedWords);
            droppedWordsRepository.deleteAllByWordType("word");

            Level level = levelRepository.findById(1).orElse(null);
            assert level != null;

            if (level.getLevel() >= 5) {
                level.setLevel(1);
                wordsRepository.deleteAllByWordType("word");
                droppedWordsRepository.deleteAllByWordType("word");

                Statistic statistic = statisticsRepository.findById(1).orElse(null);
                statistic.setCycles(statistic.getCycles() + 1);
                statisticsRepository.save(statistic);
                this.advancement = statisticsService.getCyclesAdvancement();
            } else {
                level.setLevel(level.getLevel() + 1);
            }

            levelRepository.save(level);
        } else if (wordsRepository.findAllByWordType("difficult").isEmpty()) {
            List<Word> droppedWords = modelConverter.convertDroppedWordsToWordsList(droppedWordsRepository.findAllByWordType("difficult"));
            wordsRepository.saveAll(droppedWords);
            droppedWordsRepository.deleteAllByWordType("difficult");
        }

        if (!wordDtos.isEmpty() && "difficult".equals(wordDtos.get(0).getWordType())) {
            return modelConverter.convert(wordsRepository.findAllByWordType("difficult"),
                    advancement != null ? advancement.getDescription() : null);
        }
        return modelConverter.convert(wordsRepository.findAllByWordType("word"), advancement != null ? advancement.getDescription() : null);
    }
}