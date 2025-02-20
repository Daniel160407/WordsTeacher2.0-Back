package com.wordsteacher2.controller;

import com.wordsteacher2.dto.WordDto;
import com.wordsteacher2.freemius.service.exception.NoPermissionException;
import com.wordsteacher2.service.WordDropperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> getDroppedWords(@RequestParam Integer userid,
                                             @RequestParam Integer languageid,
                                             @RequestParam Boolean tests) {
        try {
            return ResponseEntity.ok().body(wordDropperService.getDroppedWords(userid, languageid, tests));
        } catch (NoPermissionException e) {
            System.err.println("Dropper");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping
    public ResponseEntity<?> dropWords(@RequestBody List<WordDto> wordDtos) {
        return ResponseEntity.ok().body(wordDropperService.dropWords(wordDtos));
    }
}
