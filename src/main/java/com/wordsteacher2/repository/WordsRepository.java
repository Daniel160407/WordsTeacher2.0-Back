package com.wordsteacher2.repository;

import com.wordsteacher2.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordsRepository extends JpaRepository<Word, Integer> {
}
