package com.wordsteacher2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class PageController {

    @GetMapping
    public String getMainPage() {
        return "index.html";
    }

    @GetMapping(value = "/documentation")
    public String getDocPage() {
        return "index.html";
    }
}
