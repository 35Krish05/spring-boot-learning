package com.example.AdvanceJPA.service;

import com.example.AdvanceJPA.entity.Comment;
import com.example.AdvanceJPA.entity.Post;
import com.example.AdvanceJPA.entity.User;
import com.example.AdvanceJPA.repository.CommentRepository;
import com.example.AdvanceJPA.repository.PostRepository;
import com.example.AdvanceJPA.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(Long postId, Long userId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}