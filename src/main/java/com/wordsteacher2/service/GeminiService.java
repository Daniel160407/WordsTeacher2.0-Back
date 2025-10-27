package com.wordsteacher2.service;

import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    String askGemini(String prompt);
}
