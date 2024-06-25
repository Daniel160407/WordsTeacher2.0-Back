package com.wordsteacher2.util;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public List<WordDto> convertWordsToDtoList(List<Word> words) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning())));
        return wordDtos;
    }

    public WordDto convert(Word word) {
        return WordDto.builder()
                .word(word.getWord())
                .meaning(word.getMeaning())
                .build();
    }

    public Word convert(WordDto wordDto) {
        return Word.builder()
                .word(wordDto.getWord())
                .meaning(wordDto.getMeaning())
                .build();
    }
}
