package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticDto {
    private Integer wordsLearned;
    private Integer cycles;
    private Integer dayStreak;
    private List<String> advancements;
    private Integer userId;
}
