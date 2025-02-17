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
    public List<WordDto> getWords(String wordsType, Integer userId) {
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserId(wordsType, "true", userId));
    }

    @Override
    public List<WordDto> addWord(WordDto wordDto) {
        wordDto.setActive("true");
        wordsRepository.save(modelConverter.convert(wordDto));
        return modelConverter.convertWordsToDtoList(
                wordsRepository.findAllByWordTypeAndActiveAndUserId(wordDto.getWordType(), "true", wordDto.getUserId()));
    }

    @Override
    public Level getLevel(Integer userId) {
        return levelRepository.findByUserId(userId);
    }

    @Override
    public List<WordDto> changeWord(List<WordDto> wordDtos) {
        WordDto original = wordDtos.get(0);
        WordDto changed = wordDtos.get(1);

        Word foundWord = wordsRepository.findByWordAndMeaningAndUserId(original.getWord(), original.getMeaning(), original.getUserId());
        foundWord.setWord(changed.getWord());
        foundWord.setMeaning(changed.getMeaning());
        foundWord.setWordType(changed.getWordType());

        wordsRepository.save(foundWord);
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordTypeAndActiveAndUserId(
                original.getWordType(), "true", original.getUserId()));
    }

    @Override
    public List<WordDto> deleteWord(WordDto wordDto) {
        wordsRepository.deleteByWordAndMeaningAndUserId(wordDto.getWord(), wordDto.getMeaning(), wordDto.getUserId());
        return modelConverter.convertWordsToDtoList(wordsRepository.findAllByWordTypeAndActiveAndUserId(
                wordDto.getWordType(), "true", wordDto.getUserId()));
    }
}
