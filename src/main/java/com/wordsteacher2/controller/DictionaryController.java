package com.wordsteacher2.controller;

import com.wordsteacher2.dto.DictionaryDto;
import com.wordsteacher2.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wordsTeacher/dictionary")
@CrossOrigin(origins = "*")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<?> getWords(@RequestParam String type, @RequestParam Integer userid) {
        return ResponseEntity.ok().body(dictionaryService.getWords(type, userid));
    }

    @PostMapping
    public ResponseEntity<?> addWord(@RequestBody DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dictionaryService.addWord(dictionaryDto));
    }

    @PutMapping
    public ResponseEntity<?> editWord(@RequestBody List<DictionaryDto> dictionaryDtos) {
        return ResponseEntity.ok().body(dictionaryService.editWord(dictionaryDtos));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWord(@RequestParam String word, @RequestParam String meaning, @RequestParam Integer userid) {
        return ResponseEntity.ok().body(dictionaryService.deleteWord(new DictionaryDto(word, meaning, userid)));
    }
}
