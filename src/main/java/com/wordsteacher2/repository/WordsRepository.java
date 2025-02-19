package com.wordsteacher2.repository;

import com.wordsteacher2.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WordsRepository extends JpaRepository<Word, Integer> {
    @Transactional
    void deleteByWordAndMeaningAndUserIdAndLanguageId(String word, String meaning, Integer userId, Integer languageId);

    @Transactional
    void deleteAllByWordTypeAndUserIdAndLanguageId(String type, Integer userId, Integer languageId);

    @Transactional
    void deleteAllByUserIdAndLanguageId(Integer userId, Integer languageId);

    Word findByWordAndMeaningAndUserIdAndLanguageId(String word, String meaning, Integer userId, Integer languageId);

    List<Word> findAllByWordTypeAndActiveAndUserIdAndLanguageId(String wordsType, String active, Integer userId, Integer languageId);
}
