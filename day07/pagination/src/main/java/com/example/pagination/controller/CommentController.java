package com.example.pagination.controller;


import com.example.pagination.entity.Comment;
import com.example.pagination.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public Comment createComment(@PathVariable Long postId,
                                 @PathVariable Long userId,
                                 @RequestBody Comment comment) {
        return commentService.createComment(postId, userId, comment);
    }
}