package com.example.JPARelationship.controller;

import com.example.JPARelationship.entity.Comment;
import com.example.JPARelationship.service.CommentService;
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
