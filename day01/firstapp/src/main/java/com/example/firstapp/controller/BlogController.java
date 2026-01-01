package com.example.firstapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Blog API";

    }
}
