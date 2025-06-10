package com.wordsteacher2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private String word;
    private String meaning;
    private String example;
    private String wordType;
    private String active;
    private String level;
    private Integer userId;
    private Integer languageId;

    public WordDto(String word, String meaning, String example, String level, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.example = example;
        this.level = level;
        this.userId = userId;
        this.languageId = languageId;
    }
}
