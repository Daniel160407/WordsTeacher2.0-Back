package com.wordsteacher2.util;

import com.wordsteacher2.dto.*;
import com.wordsteacher2.model.Dictionary;
import com.wordsteacher2.model.User;
import com.wordsteacher2.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public List<WordDto> convertWordsToDtoList(List<Word> words) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning(), word.getWordType(), word.getActive(), word.getUserId())));
        return wordDtos;
    }

    public List<DictionaryDto> convertDictionaryToDtoList(List<Dictionary> words) {
        List<DictionaryDto> convertedDictionary = new ArrayList<>();
        words.forEach(word -> convertedDictionary.add(new DictionaryDto(word.getWord(), word.getMeaning(), word.getUserId())));
        return convertedDictionary;
    }

    public Word convert(WordDto wordDto) {
        return Word.builder()
                .word(wordDto.getWord())
                .meaning(wordDto.getMeaning())
                .wordType(wordDto.getWordType())
                .active(wordDto.getActive())
                .userId(wordDto.getUserId())
                .build();
    }

    public Dictionary convert(DictionaryDto dictionaryDto) {
        return Dictionary.builder()
                .word(dictionaryDto.getWord())
                .meaning(dictionaryDto.getMeaning())
                .userId(dictionaryDto.getUserId())
                .build();
    }

    public WordListWithAdvancementDto convert(List<Word> words, String advancement) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning(), word.getUserId())));
        return WordListWithAdvancementDto.builder()
                .wordDtos(wordDtos)
                .advancement(advancement)
                .build();
    }

    public DictionaryListWithAdvancementDto convertDict(List<Dictionary> dictionary, String advancement) {
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        dictionary.forEach(word -> dictionaryDtos.add(new DictionaryDto(word.getWord(), word.getMeaning(), word.getUserId())));
        return DictionaryListWithAdvancementDto.builder()
                .dictionaryDtos(dictionaryDtos)
                .advancement(advancement)
                .build();
    }
    public User convert(UserDto userDto){
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
