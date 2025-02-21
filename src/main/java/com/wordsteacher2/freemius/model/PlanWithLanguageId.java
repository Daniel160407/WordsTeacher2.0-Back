package com.wordsteacher2.freemius.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanWithLanguageId {
    private Integer userId;
    private String plan;
    private Integer languageId;
}
