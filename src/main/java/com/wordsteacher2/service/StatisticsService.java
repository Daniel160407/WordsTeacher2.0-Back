package com.wordsteacher2.service;

import com.wordsteacher2.service.advancement.Advancement;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {
    Advancement checkStatistics();
    Advancement getLearnedWordsAdvancement();
    Advancement getCyclesAdvancement();
    Advancement getDayStreakAdvancement();
}
