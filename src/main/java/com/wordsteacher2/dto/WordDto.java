package com.wordsteacher2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class WordDto {
    private String word;
    private String meaning;
    private String wordType;

    public WordDto(String word, String meaning, String wordType) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
    }

    public WordDto(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
}
