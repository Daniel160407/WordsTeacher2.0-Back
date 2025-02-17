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
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "word")
    private String word;
    @Column(name = "meaning")
    private String meaning;
    @Column(name = "word_type")
    private String wordType;
    @Column(name = "active")
    private String active;
    @Column(name = "user_id")
    private Integer userId;


    public Word(String word, String meaning, String wordType, String active, Integer userId) {
        this.word = word;
        this.meaning = meaning;
        this.wordType = wordType;
        this.active = active;
        this.userId = userId;
    }
}
