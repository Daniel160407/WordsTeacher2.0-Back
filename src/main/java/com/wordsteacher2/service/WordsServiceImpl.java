package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.repository.LevelRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordsServiceImpl implements WordsService {
    private final WordsRepository wordsRepository;
    private final LevelRepository levelRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public WordsServiceImpl(WordsRepository wordsRepository, LevelRepository levelRepository, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.levelRepository = levelRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> getWords() {
        return modelConverter.convertWordsToDtoList(wordsRepository.findAll());
    }

    @Override
    public List<WordDto> addWord(WordDto wordDto) {
        wordsRepository.save(modelConverter.convert(wordDto));
        return modelConverter.convertWordsToDtoList(wordsRepository.findAll());
    }

    @Override
    public Level getLevel() {
        return levelRepository.findById(1).orElse(null);
    }
}
