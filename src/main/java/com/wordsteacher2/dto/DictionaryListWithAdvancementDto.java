package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DictionaryListWithAdvancementDto {
    private List<DictionaryDto> dictionaryDtos;
    private String advancement;
}
