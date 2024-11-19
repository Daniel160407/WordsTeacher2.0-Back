package com.wordsteacher2.dto;

import lombok.*;

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
