package com.wordsteacher2.controller;

import com.wordsteacher2.dto.LanguageDto;
import com.wordsteacher2.service.LanguagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
@CrossOrigin(origins = "*")
public class LanguageController {
    private final LanguagesService languagesService;

    @Autowired
    public LanguageController(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }

    @GetMapping
    public ResponseEntity<?> getLanguages(@RequestParam Integer userid) {
        return ResponseEntity.ok(languagesService.getUserLanguages(userid));
    }

    @GetMapping("/id")
    public ResponseEntity<?> getLanguageId(@RequestParam String language, @RequestParam Integer userid) {
        return ResponseEntity.ok(languagesService.getLanguageId(language, userid));
    }

    @PostMapping
    public ResponseEntity<?> addLanguage(@RequestBody LanguageDto language) {
        return ResponseEntity.status(HttpStatus.CREATED).body(languagesService.addLanguage(language));
    }

    @DeleteMapping
    public ResponseEntity<?> removeLanguage(@RequestParam String language, @RequestParam Integer userid) {
        return ResponseEntity.ok(languagesService.removeLanguage(language, userid));
    }
}
