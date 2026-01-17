package com.example.security_demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    @GetMapping("/posts")
    public String posts() {
        return "Public posts visible to everyone";
    }

    @PostMapping("/posts")
    public String createPost() {
        return "Post created (should require login)";
    }
    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        return "Deleted post id = " + id;
    }
    @PutMapping("/posts/{id}")
    public String updatePost(@PathVariable Long id) {
        return "Updated post id = " + id;
    }

}
