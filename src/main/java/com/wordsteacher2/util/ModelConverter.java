package com.wordsteacher2.util;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.dto.DictionaryListWithAdvancementDto;
import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.dto.WordListWithAdvancementDto;
import com.wordsteacher2.model.Dictionary;
import com.wordsteacher2.model.DroppedWord;
import com.wordsteacher2.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelConverter {
    public List<WordDto> convertWordsToDtoList(List<Word> words) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning(), word.getWordType())));
        return wordDtos;
    }

    public List<WordDto> convertDroppedWordsToDtoList(List<DroppedWord> droppedWords) {
        List<WordDto> wordDtos = new ArrayList<>();
        droppedWords.forEach(droppedWord -> wordDtos.add(new WordDto(droppedWord.getWord(), droppedWord.getMeaning(), droppedWord.getWordType())));
        return wordDtos;
    }

    public List<Word> convertDroppedWordsToWordsList(List<DroppedWord> droppedWords) {
        List<Word> words = new ArrayList<>();
        droppedWords.forEach(droppedWord -> words.add(new Word(droppedWord.getWord(), droppedWord.getMeaning(), droppedWord.getWordType())));
        return words;
    }

    public List<DroppedWord> convertDtoToDroppedWordsList(List<WordDto> wordDtos) {
        List<DroppedWord> droppedWords = new ArrayList<>();
        wordDtos.forEach(wordDto -> droppedWords.add(new DroppedWord(wordDto.getWord(), wordDto.getMeaning(), wordDto.getWordType())));
        return droppedWords;
    }

    public List<DictionaryDto> convertDictionaryToDtoList(List<Dictionary> words) {
        List<DictionaryDto> convertedDictionary = new ArrayList<>();
        words.forEach(word -> convertedDictionary.add(new DictionaryDto(word.getWord(), word.getMeaning())));
        return convertedDictionary;
    }

    public Word convert(WordDto wordDto) {
        return Word.builder()
                .word(wordDto.getWord())
                .meaning(wordDto.getMeaning())
                .wordType(wordDto.getWordType())
                .build();
    }

    public Dictionary convert(DictionaryDto dictionaryDto) {
        return Dictionary.builder()
                .word(dictionaryDto.getWord())
                .meaning(dictionaryDto.getMeaning())
                .build();
    }

    public WordListWithAdvancementDto convert(List<Word> words, String advancement) {
        List<WordDto> wordDtos = new ArrayList<>();
        words.forEach(word -> wordDtos.add(new WordDto(word.getWord(), word.getMeaning())));
        return WordListWithAdvancementDto.builder()
                .wordDtos(wordDtos)
                .advancement(advancement)
                .build();
    }

    public DictionaryListWithAdvancementDto convertDict(List<Dictionary> dictionary, String advancement) {
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        dictionary.forEach(word -> dictionaryDtos.add(new DictionaryDto(word.getWord(), word.getMeaning())));
        return DictionaryListWithAdvancementDto.builder()
                .dictionaryDtos(dictionaryDtos)
                .advancement(advancement)
                .build();
    }
}
