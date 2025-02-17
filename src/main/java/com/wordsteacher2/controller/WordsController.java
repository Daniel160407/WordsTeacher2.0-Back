package com.wordsteacher2.controller;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getWords(@RequestParam String wordstype, @RequestParam Integer userid) {
        return ResponseEntity.ok().body(wordsService.getWords(wordstype, userid));
    }

    @GetMapping("/level")
    public ResponseEntity<?> getLevel(@RequestParam Integer userid) {
        return ResponseEntity.ok().body(wordsService.getLevel(userid));
    }

    @PostMapping
    public ResponseEntity<?> addWord(@RequestBody WordDto wordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wordsService.addWord(wordDto));
    }

    @PutMapping
    public ResponseEntity<?> changeWord(@RequestBody List<WordDto> wordDtos) {
        return ResponseEntity.ok().body(wordsService.changeWord(wordDtos));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWord(@RequestParam String word,
                                        @RequestParam String meaning,
                                        @RequestParam String wordtype,
                                        @RequestParam Integer userid) {
        return ResponseEntity.ok().body(wordsService.deleteWord(new WordDto(word, meaning, wordtype, userid)));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
    }
}
