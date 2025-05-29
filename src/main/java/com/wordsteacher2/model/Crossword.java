package com.wordsteacher2.model;

import com.wordsteacher2.service.direction.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Crossword {
    private String word;
    private int length;
    private String hint;
    private Direction direction;
    private int crossIndex;
}
