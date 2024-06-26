package com.wordsteacher2.util;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.model.DroppedWord;
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

    public List<Word> convertDtoToWordsList(List<WordDto> wordDtos) {
        List<Word> words = new ArrayList<>();
        wordDtos.forEach(wordDto -> words.add(new Word(wordDto.getWord(), wordDto.getMeaning())));
        return words;
    }

    public List<DroppedWord> convert(List<WordDto> wordDtos) {
        List<DroppedWord> droppedWords = new ArrayList<>();
        wordDtos.forEach(wordDto -> droppedWords.add(new DroppedWord(wordDto.getWord(), wordDto.getMeaning())));
        return droppedWords;
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
