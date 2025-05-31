package com.wordsteacher2.service;

import com.wordsteacher2.dto.StatisticDto;
import com.wordsteacher2.service.advancement.Advancement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    Advancement checkStatistics();

    Advancement getLearnedWordsAdvancement(Integer userId, Integer languageId);

    Advancement getCyclesAdvancement(Integer userId, Integer languageId);

    Advancement getDayStreakAdvancement(Integer userId, Integer languageId);

    StatisticDto getStatistics(Integer userId, Integer languageId);

    List<String> getAdvancements();
}
