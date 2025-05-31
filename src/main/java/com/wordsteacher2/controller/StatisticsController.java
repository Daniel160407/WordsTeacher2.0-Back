package com.wordsteacher2.controller;

import com.wordsteacher2.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<?> getStatistics(@RequestParam Integer userid, @RequestParam Integer languageid) {
        return ResponseEntity.ok(statisticsService.getStatistics(userid, languageid));
    }

    @GetMapping("/advancements")
    public ResponseEntity<?> getAdvancements() {
        return ResponseEntity.ok(statisticsService.getAdvancements());
    }
}
