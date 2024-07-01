package com.wordsteacher2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DictionaryDto {
    private String word;
    private String meaning;
}
