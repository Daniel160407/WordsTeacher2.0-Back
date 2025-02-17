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
    public List<WordDto> getDroppedWords() {
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordTypeAndActive("word","false"));
    }

    @Override
    public WordListWithAdvancementDto dropWords(List<WordDto> wordDtos) {
        for (WordDto wordDto : wordDtos) {
            Word word = wordsRepository.findByWordAndMeaning(wordDto.getWord(), wordDto.getMeaning());
            word.setActive("false");
            wordsRepository.save(word);
        }

        if (wordsRepository.findAllByWordTypeAndActive("word", "true").isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActive("word", "false");
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }

            Level level = levelRepository.findById(1).orElse(null);
            assert level != null;

            if (level.getLevel() >= 5) {
                level.setLevel(1);
                wordsRepository.deleteAllByWordType("word");

                Statistic statistic = statisticsRepository.findById(1).orElse(null);
                statistic.setCycles(statistic.getCycles() + 1);
                statisticsRepository.save(statistic);
                this.advancement = statisticsService.getCyclesAdvancement();
            } else {
                level.setLevel(level.getLevel() + 1);
            }

            levelRepository.save(level);
        } else if (wordsRepository.findAllByWordTypeAndActive("difficult", "true").isEmpty()) {
            List<Word> droppedWords = wordsRepository.findAllByWordTypeAndActive("difficult", "false");
            for (Word droppedWord : droppedWords) {
                droppedWord.setActive("true");
                wordsRepository.save(droppedWord);
            }
        }

        if (!wordDtos.isEmpty() && "difficult".equals(wordDtos.get(0).getWordType())) {
            return modelConverter.convert(wordsRepository.findAllByWordTypeAndActive("difficult", "true"),
                    advancement != null ? advancement.getDescription() : null);
        }
        return modelConverter.convert(wordsRepository.findAllByWordTypeAndActive("word", "true"), advancement != null ? advancement.getDescription() : null);
    }
}