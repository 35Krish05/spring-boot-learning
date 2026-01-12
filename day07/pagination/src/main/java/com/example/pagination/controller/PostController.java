package com.example.pagination.controller;


import com.example.pagination.entity.Post;
import com.example.pagination.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping
    public Page<Post> getPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return postService.getPosts(pageable);
    }
    @GetMapping("/sorted/title")
    public Page<Post> getPostsSortedByTitle(Pageable pageable) {

        Pageable newPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("title").ascending()
        );
        return postService.getPosts(newPageable);
    }

//    //Find post by user
//    @GetMapping("/user/{userId}")
//    public List<Post> findPostsByUserId(@PathVariable("userId") Long userId){
//        return postService.findPostsByUserId(userId);
//    }
//    //Find posts containing keyword in title
//    @GetMapping("/search")
//    public List<Post> searchPosts(@RequestParam String keyword) {
//        return postService.searchPostsByTitle(keyword);    //http://localhost:8080/api/posts/search?keyword=first
//    }
//    //Find recent posts(ordered by date)
//    @GetMapping("/recent/{days}")
//    public Page<Post> getRecentPosts(Pageable pageable,@PathVariable("days") int days) {
//        return postService.getRecentPosts(pageable,days);
//    }
}