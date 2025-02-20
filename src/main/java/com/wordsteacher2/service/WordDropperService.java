package com.wordsteacher2.service;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.dto.WordListWithAdvancementDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordDropperService {
    List<WordDto> getDroppedWords(Integer userId, Integer languageId, Boolean tests);

    WordListWithAdvancementDto dropWords(List<WordDto> wordDtos);
}
