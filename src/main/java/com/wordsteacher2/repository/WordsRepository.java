package com.wordsteacher2.repository;

import com.wordsteacher2.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface WordsRepository extends JpaRepository<Word, Integer> {
    @Transactional
    void deleteByWordAndMeaning(String word, String meaning);
}
