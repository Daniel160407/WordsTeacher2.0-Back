package com.wordsteacher2.controller;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.service.WordDropperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wordsTeacher/dropper")
@CrossOrigin(origins = "*")
public class WordsDropperController {
    private final WordDropperService wordDropperService;

    @Autowired
    public WordsDropperController(WordDropperService wordDropperService) {
        this.wordDropperService = wordDropperService;
    }

    @PutMapping
    public ResponseEntity<?> dropWords(@RequestBody List<WordDto> wordDtos) {
        return ResponseEntity.ok().body(wordDropperService.dropWords(wordDtos));
    }
}
