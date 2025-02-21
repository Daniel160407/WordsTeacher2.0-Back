package com.wordsteacher2.repository;

import com.wordsteacher2.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    Level findByUserIdAndLanguageId(Integer userId, Integer LanguageId);

    Level findByUserId(Integer userId);

    @Transactional
    void deleteByUserIdAndLanguageId(Integer userId, Integer languageId);
}