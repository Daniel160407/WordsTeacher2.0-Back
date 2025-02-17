package com.wordsteacher2.repository;

import com.wordsteacher2.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WordsRepository extends JpaRepository<Word, Integer> {
    @Transactional
    void deleteByWordAndMeaningAndUserId(String word, String meaning, Integer userId);

    @Transactional
    void deleteAllByWordTypeAndUserId(String type, Integer userId);

    Word findByWordAndMeaningAndUserId(String word, String meaning, Integer userId);

    List<Word> findAllByWordTypeAndActiveAndUserId(String wordsType, String active, Integer userId);
}
