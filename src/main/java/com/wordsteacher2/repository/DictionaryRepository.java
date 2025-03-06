package com.wordsteacher2.repository;

import com.wordsteacher2.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {
    @Query("SELECT d FROM Dictionary d WHERE d.userId = :userId AND d.languageId = :languageId ORDER BY " +
            "CASE " +
            "  WHEN LOWER(SUBSTRING(d.word, 1, 3)) IN ('der', 'die', 'das') THEN LOWER(SUBSTRING(d.word, 5)) " +
            "  WHEN LOWER(SUBSTRING(d.word, 1, 4)) = 'sich' THEN LOWER(SUBSTRING(d.word, 6)) " +
            "  ELSE LOWER(d.word) " +
            "END ASC")
    List<Dictionary> findAllSortedByFirstLetterAndByUserIdAndLanguageId(
            @Param("userId") Integer userId,
            @Param("languageId") Integer languageId
    );

    @Transactional
    void deleteByWordAndMeaningAndUserIdAndLanguageId(String word, String meaning, Integer userId, Integer languageId);

    @Transactional
    void deleteAllByUserIdAndLanguageId(Integer userId, Integer languageId);

    @Transactional
    void deleteAllByUserId(Integer userId);

    Dictionary findByWordAndMeaningAndUserIdAndLanguageId(String word, String meaning, Integer userId, Integer languageId);
}
