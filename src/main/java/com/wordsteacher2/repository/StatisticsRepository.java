package com.wordsteacher2.repository;

import com.wordsteacher2.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic, Integer> {
    Statistic findByUserIdAndLanguageId(Integer userId, Integer languageId);

    @Transactional
    void deleteByUserIdAndLanguageId(Integer userId, Integer languageId);

    @Transactional
    void deleteAllByUserId(Integer userId);
}