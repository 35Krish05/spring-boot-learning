package com.example.AdvanceJPA.controller;


import com.example.AdvanceJPA.entity.Post;
import com.example.AdvanceJPA.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    //Find post by user
    @GetMapping("/user/{userId}")
    public List<Post> findPostsByUserId(@PathVariable("userId") Long userId){
        return postService.findPostsByUserId(userId);
    }
    //Find posts containing keyword in title
    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String keyword) {
        return postService.searchPostsByTitle(keyword);    //http://localhost:8080/api/posts/search?keyword=first
    }
   //Find recent posts(ordered by date)
    @GetMapping("/recent/{days}")
    public Page<Post> getRecentPosts(Pageable pageable,@PathVariable("days") int days) {
        return postService.getRecentPosts(pageable,days);
    }
}