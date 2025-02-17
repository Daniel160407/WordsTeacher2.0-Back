package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordsService {
    List<WordDto> getWords(String wordsType, Integer userId);

    List<WordDto> addWord(WordDto wordDto);

    Level getLevel(Integer userId);

    List<WordDto> changeWord(List<WordDto> wordDtos);
    List<WordDto> deleteWord(WordDto wordDto);
}
