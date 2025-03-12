package com.wordsteacher2.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDto {
    private String word;
    private String meaning;
    private String level;
    private Integer userId;
    private Integer LanguageId;

    public DictionaryDto(String word, String meaning, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.userId = userId;
        LanguageId = languageId;
    }
}
