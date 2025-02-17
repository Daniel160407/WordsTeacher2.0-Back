package com.wordsteacher2.dto;

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
    private Integer userId;
}
