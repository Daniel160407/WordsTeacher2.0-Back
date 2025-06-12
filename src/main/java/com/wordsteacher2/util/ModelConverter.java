package com.wordsteacher2.util;

import com.wordsteacher2.dto.*;
import com.wordsteacher2.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ModelConverter {
    public List<WordDto> convertWordsToDtoList(List<Word> words) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning(), word.getExample(), word.getWordType(), word.getActive(), word.getLevel(), word.getUserId(), word.getLanguageId())));
        return wordDtos;
    }

    public List<DictionaryDto> convertDictionaryToDtoList(List<Dictionary> words) {
        List<DictionaryDto> convertedDictionary = new ArrayList<>();
        words.forEach(word -> convertedDictionary.add(new DictionaryDto(word.getWord(), word.getMeaning(), word.getExample(), word.getLevel(), word.getUserId(), word.getLanguageId())));
        return convertedDictionary;
    }

    public Word convert(WordDto wordDto) {
        return Word.builder()
                .word(wordDto.getWord())
                .meaning(wordDto.getMeaning())
                .example(wordDto.getExample())
                .wordType(wordDto.getWordType())
                .active(wordDto.getActive())
                .level(wordDto.getLevel())
                .userId(wordDto.getUserId())
                .languageId(wordDto.getLanguageId())
                .build();
    }

    public Dictionary convert(DictionaryDto dictionaryDto) {
        return Dictionary.builder()
                .word(dictionaryDto.getWord())
                .meaning(dictionaryDto.getMeaning())
                .example(dictionaryDto.getExample())
                .level(dictionaryDto.getLevel())
                .userId(dictionaryDto.getUserId())
                .languageId(dictionaryDto.getLanguageId())
                .build();
    }

    public WordListWithAdvancementDto convert(List<Word> words, String advancement) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning(), word.getExample(), word.getLevel(), word.getUserId(), word.getLanguageId())));
        return WordListWithAdvancementDto.builder()
                .wordDtos(wordDtos)
                .advancement(advancement)
                .build();
    }

    public DictionaryListWithAdvancementDto convertDict(List<Dictionary> dictionary, String advancement) {
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        dictionary.forEach(word -> dictionaryDtos.add(new DictionaryDto(word.getWord(), word.getMeaning(), word.getExample(), word.getLevel(), word.getUserId(), word.getLanguageId())));
        return DictionaryListWithAdvancementDto.builder()
                .dictionaryDtos(dictionaryDtos)
                .advancement(advancement)
                .build();
    }

    public User convert(UserDto userDto, String registrationDate) {
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .plan(userDto.getPlan())
                .registrationDate(registrationDate)
                .build();
    }

    public List<LanguageDto> convertLanguagesToDtoList(List<Language> languages) {
        List<LanguageDto> languageDtos = new ArrayList<>();
        languages.forEach(language -> languageDtos.add(new LanguageDto(language.getLanguage(), language.getUserId())));
        return languageDtos;
    }

    public Language convert(LanguageDto languageDto) {
        return Language.builder()
                .language(languageDto.getLanguage())
                .userId(languageDto.getUserId())
                .build();
    }

    public StatisticDto convert(Statistic statistic, String advancement) {
        List<String> advancementsList = statistic.getAdvancements() != null && !statistic.getAdvancements().isBlank()
                ? Arrays.asList(statistic.getAdvancements().split(",\\s*"))
                : Collections.emptyList();

        return StatisticDto.builder()
                .wordsLearned(statistic.getWordsLearned())
                .cycles(statistic.getCycles())
                .dayStreak(statistic.getDayStreak())
                .advancements(advancementsList)
                .advancement(advancement)
                .userId(statistic.getUserId())
                .build();
    }
}
