package com.wordsteacher2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LevelDto {
    private Integer level;
    private Integer userId;
}
