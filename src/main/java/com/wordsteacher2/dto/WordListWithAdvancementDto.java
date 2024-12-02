package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WordListWithAdvancementDto {
    private List<WordDto> wordDtos;
    private String advancement;
}
