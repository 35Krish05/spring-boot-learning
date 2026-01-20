package com.example.JWTAuthentication.controller;

import com.example.JWTAuthentication.dto.mapper.PostMapper;
import com.example.JWTAuthentication.dto.requestDTO.PostRequest;
import com.example.JWTAuthentication.dto.responseDTO.PostDTO;
import com.example.JWTAuthentication.entity.Post;
import com.example.JWTAuthentication.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    // ✅ CREATE (any authenticated user)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostRequest request) {
        Post post = postService.createPost(request);
        return postMapper.toDto(post);
    }

    // ✅ GET ALL
    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    // ✅ UPDATE (only owner)
    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        Post updated = postService.updatePost(id, request);
        return postMapper.toDto(updated);
    }

    // ✅ DELETE (only owner)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
