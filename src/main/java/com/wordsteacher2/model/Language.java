package com.wordsteacher2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "language")
    private String language;
    @Column(name = "user_id")
    private Integer userId;

    public Language(String language, Integer userId) {
        this.language = language;
        this.userId = userId;
    }
}
