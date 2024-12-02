package com.wordsteacher2.repository;

import com.wordsteacher2.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic, Integer> {
}
