package com.wordsteacher2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class WordDto {
    private String word;
    private String meaning;
    private String wordType;
    private String active;
    private Integer userId;

    public WordDto(String word, String meaning, String wordType, String active, Integer userId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.active = active;
        this.userId = userId;
    }

    public WordDto(String word, String meaning, String wordType, Integer userId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.userId = userId;
    }

    public WordDto(String word, String meaning, Integer userId) {
        this.word = word;
        this.meaning = meaning;
        this.userId = userId;
    }
}
