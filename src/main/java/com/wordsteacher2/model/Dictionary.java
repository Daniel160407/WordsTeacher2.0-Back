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
@Table(name = "dictionary")
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "word")
    private String word;
    @Column(name = "meaning")
    private String meaning;
    @Column(name = "example")
    private String example;
    @Column(name = "level")
    private String level;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "language_id")
    private Integer languageId;

    public Dictionary(String word, String meaning, String example, Integer userId, Integer languageId) {
        this.word = word;
        this.meaning = meaning;
        this.example = example;
        this.userId = userId;
        this.languageId = languageId;
    }
}
