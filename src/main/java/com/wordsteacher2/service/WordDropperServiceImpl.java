package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.repository.DroppedWordsRepository;
import com.wordsteacher2.repository.WordsRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordDropperServiceImpl implements WordDropperService {
    private final WordsRepository wordsRepository;
    private final DroppedWordsRepository droppedWordsRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public WordDropperServiceImpl(WordsRepository wordsRepository, DroppedWordsRepository droppedWordsRepository, ModelConverter modelConverter) {
        this.wordsRepository = wordsRepository;
        this.droppedWordsRepository = droppedWordsRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<WordDto> dropWords(List<WordDto> wordDtos) {
        for (WordDto wordDto : wordDtos) {
            wordsRepository.deleteByWordAndMeaning(wordDto.getWord(), wordDto.getMeaning());
        }
        droppedWordsRepository.saveAll(modelConverter.convert(wordDtos));
        return modelConverter.convertWordsToDtoList(wordsRepository.findAll());
    }
}
