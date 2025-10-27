package com.wordsteacher2.controller;

import com.wordsteacher2.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordsTeacher/genai")
@CrossOrigin(origins = "*")
public class GeminiController {
    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<?> askGemini(@RequestBody String prompt) {
        return ResponseEntity.ok(geminiService.askGemini(prompt));
    }
}
