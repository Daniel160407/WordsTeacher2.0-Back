package com.wordsteacher2.controller;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wordsTeacher/words")
@CrossOrigin(origins = "*")
public class WordsController {
    private final WordsService wordsService;

    @Autowired
    public WordsController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping
    public ResponseEntity<?> getWords() {
        return ResponseEntity.ok().body(wordsService.getWords());
    }

    @GetMapping("/level")
    public ResponseEntity<?> getLevel() {
        return ResponseEntity.ok().body(wordsService.getLevel());
    }

    @PostMapping
    public ResponseEntity<?> addWord(@RequestBody WordDto wordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wordsService.addWord(wordDto));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
    }
}
