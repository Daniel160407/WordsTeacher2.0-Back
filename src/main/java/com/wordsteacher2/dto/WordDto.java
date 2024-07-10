package com.wordsteacher2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private String word;
    private String meaning;
    private String wordType;

    public WordDto(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
}
