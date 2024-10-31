package com.wordsteacher2.service;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.model.Dictionary;
import com.wordsteacher2.repository.DictionaryRepository;
import com.wordsteacher2.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final ModelConverter modelConverter;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, ModelConverter modelConverter) {
        this.dictionaryRepository = dictionaryRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<DictionaryDto> getWords() {
        List<Dictionary> words = dictionaryRepository.findAll();
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }

    @Override
    public List<DictionaryDto> addWord(DictionaryDto dictionaryDto) {
        Dictionary existedWord = dictionaryRepository.findByWordAndMeaning(dictionaryDto.getWord(), dictionaryDto.getMeaning());
        if (existedWord == null) {
            dictionaryRepository.save(modelConverter.convert(dictionaryDto));
        }
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }

    @Override
    public List<DictionaryDto> deleteWord(DictionaryDto dictionaryDto) {
        dictionaryRepository.deleteByWordAndMeaning(dictionaryDto.getWord(), dictionaryDto.getMeaning());
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }

    @Override
    public List<DictionaryDto> editWord(List<DictionaryDto> dictionaryDtos) {
        DictionaryDto original = dictionaryDtos.get(0);
        DictionaryDto changed = dictionaryDtos.get(1);

        Dictionary dictionary = dictionaryRepository.findByWordAndMeaning(original.getWord(), original.getMeaning());
        dictionary.setWord(changed.getWord());
        dictionary.setMeaning(changed.getMeaning());

        dictionaryRepository.save(dictionary);
        return modelConverter.convertDictionaryToDtoList(dictionaryRepository.findAllSortedByFirstLetter());
    }
}
