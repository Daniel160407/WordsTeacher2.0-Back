package com.wordsteacher2.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {
    private final Client client = new Client();

    @Override
    public String askGemini(String prompt) {
        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null);
        return response.text();
    }
}
