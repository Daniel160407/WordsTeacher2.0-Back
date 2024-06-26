package com.wordsteacher2.repository;

import com.wordsteacher2.model.DroppedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroppedWordsRepository extends JpaRepository<DroppedWord, Integer> {
}
