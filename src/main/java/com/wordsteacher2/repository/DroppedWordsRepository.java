package com.wordsteacher2.repository;

import com.wordsteacher2.model.DroppedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DroppedWordsRepository extends JpaRepository<DroppedWord, Integer> {
    @Transactional
    void deleteByWordAndMeaning(String word, String meaning);
    @Transactional
    void deleteAllByWordType(String type);

    List<DroppedWord> findAllByWordType(String type);
}
