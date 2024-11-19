package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Word;
import com.wordsteacher2.repository.DroppedWordsRepository;
import com.wordsteacher2.repository.LevelRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordDropperServiceImpl implements WordDropperService {
    private final WordsRepository wordsRepository;
    private final DroppedWordsRepository droppedWordsRepository;
    private final LevelRepository levelRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public WordDropperServiceImpl(WordsRepository wordsRepository, DroppedWordsRepository droppedWordsRepository, LevelRepository levelRepository, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.droppedWordsRepository = droppedWordsRepository;
        this.levelRepository = levelRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getDroppedWords() {
        return modelConverter.convertDroppedWordsToDtoList(droppedWordsRepository.findAll());
    }

    @Override
    public List<WordDto> dropWords(List<WordDto> wordDtos) {
        for (WordDto wordDto : wordDtos) {
            wordsRepository.deleteByWordAndMeaning(wordDto.getWord(), wordDto.getMeaning());
        }
        droppedWordsRepository.saveAll(modelConverter.convertDtoToDroppedWordsList(wordDtos));
        if (wordsRepository.findAllByWordType("word").isEmpty()) {
            List<Word> droppedWords = modelConverter.convertDroppedWordsToWordsList(droppedWordsRepository.findAllByWordType("word"));
            wordsRepository.saveAll(droppedWords);
            droppedWordsRepository.deleteAllByWordType("word");

            Level level = levelRepository.findById(1).orElse(null);
            assert level != null;

            if (level.getLevel() >= 5) {
                level.setLevel(1);
                wordsRepository.deleteAllByWordType("word");
                droppedWordsRepository.deleteAllByWordType("word");
            } else {
                level.setLevel(level.getLevel() + 1);
            }

            levelRepository.save(level);
        } else if (wordsRepository.findAllByWordType("difficult").isEmpty()) {
            List<Word> droppedWords = modelConverter.convertDroppedWordsToWordsList(droppedWordsRepository.findAllByWordType("difficult"));
            wordsRepository.saveAll(droppedWords);
            droppedWordsRepository.deleteAllByWordType("difficult");
        }

        if (wordDtos.get(0).getWordType().equals("difficult")) {
            return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType("difficult"));
        }
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType("word"));
    }
}