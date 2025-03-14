package com.wordsteacher2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "level")
    private Integer level;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "language_id")
    private Integer languageId;

    public Level(Integer level, Integer userId, Integer languageId) {
        this.level = level;
        this.userId = userId;
        this.languageId = languageId;
    }
}
