package com.example.DTO.controller;

import com.example.DTO.dto.requestDTO.CreatePostRequest;
import com.example.DTO.dto.responseDTO.PostDTO;
import com.example.DTO.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }
}
