package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordsServiceImpl implements WordsService {
    private final WordsRepository wordsRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public WordsServiceImpl(WordsRepository wordsRepository, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
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
}
