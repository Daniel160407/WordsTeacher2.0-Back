package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Level;
import com.wordsteacher2.model.Word;
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
    public List<WordDto> getWords(String wordsType) {
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType(wordsType));
    }

    @Override
    public List<WordDto> addWord(WordDto wordDto) {
        wordsRepository.save(modelConverter.convert(wordDto));
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType(wordDto.getWordType()));
    }

    @Override
    public Level getLevel() {
        return levelRepository.findById(1).orElse(null);
    }

    @Override
    public List<WordDto> changeWord(List<WordDto> wordDtos) {
        WordDto original = wordDtos.get(0);
        WordDto changed = wordDtos.get(1);

        Word foundWord = wordsRepository.findByWordAndMeaning(original.getWord(), original.getMeaning());
        foundWord.setWord(changed.getWord());
        foundWord.setMeaning(changed.getMeaning());
        foundWord.setWordType(changed.getWordType());

        wordsRepository.save(foundWord);
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType(original.getWordType()));
    }

    @Override
    public List<WordDto> deleteWord(WordDto wordDto) {
        wordsRepository.deleteByWordAndMeaning(wordDto.getWord(), wordDto.getMeaning());
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordType(wordDto.getWordType()));
    }
}
