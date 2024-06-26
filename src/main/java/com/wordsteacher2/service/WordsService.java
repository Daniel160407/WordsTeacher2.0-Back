package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordsService {
    List<WordDto> getWords();

    List<WordDto> addWord(WordDto wordDto);

    Level getLevel();
}
