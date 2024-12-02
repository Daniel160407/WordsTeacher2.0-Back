package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticDto {
    private Integer wordsLearned;
    private Integer cycles;
    private Integer dayStreak;
}
