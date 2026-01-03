package com.example.JPARelationship.controller;

import com.example.JPARelationship.entity.Post;
import com.example.JPARelationship.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}")
    public Post createPost(@PathVariable Long userId, @RequestBody Post post) {
        return postService.createPost(userId, post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }
}
