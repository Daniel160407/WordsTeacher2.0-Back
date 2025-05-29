package com.wordsteacher2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private String word;
    private String meaning;
    private String wordType;
    private String active;
    private String level;
    private Integer userId;
    private Integer languageId;

    public WordDto(String word, String meaning, String wordType, String active, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.active = active;
        this.userId = userId;
        this.languageId = languageId;
    }

    public WordDto(String word, String meaning, String wordType, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.userId = userId;
        this.languageId = languageId;
    }

    public WordDto(String word, String meaning, String wordType, String level, Integer userId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.level = level;
        this.userId = userId;
    }

    public WordDto(String word, String meaning, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.userId = userId;
        this.languageId = languageId;
    }
}
