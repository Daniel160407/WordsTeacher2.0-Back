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
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "learned_words")
    private Integer wordsLearned;
    @Column(name = "cycles")
    private Integer cycles;
    @Column(name = "day_streak")
    private Integer dayStreak;
    @Column(name = "advancements")
    private String advancements;
    @Column(name = "last_activity")
    private String lastActivityDate;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "language_id")
    private Integer languageId;

    public Statistic(Integer wordsLearned, Integer cycles, Integer dayStreak, String advancements, String lastActivityDate, Integer userId, Integer languageId) {
        this.wordsLearned = wordsLearned;
        this.cycles = cycles;
        this.dayStreak = dayStreak;
        this.advancements = advancements;
        this.lastActivityDate = lastActivityDate;
        this.userId = userId;
        this.languageId = languageId;
    }
}
