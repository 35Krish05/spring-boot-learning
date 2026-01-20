package com.example.JWTAuthentication.service;

import com.example.JWTAuthentication.dto.requestDTO.PostRequest;
import com.example.JWTAuthentication.entity.Post;
import com.example.JWTAuthentication.entity.User;
import com.example.JWTAuthentication.repository.PostRepository;
import com.example.JWTAuthentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // ✅ Create post (logged-in user becomes owner)
    @Transactional
    public Post createPost(PostRequest request) {

        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentEmail));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }

    // ✅ Get all posts (any authenticated)
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // ✅ Update post (only owner allowed)
    @Transactional
    public Post updatePost(Long postId, PostRequest request) {

        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        // ✅ Ownership check
        String ownerEmail = post.getUser().getEmail(); // ✅ now safe because transaction open
        if (!ownerEmail.equals(currentEmail)) {
            throw new AccessDeniedException("You can only edit your own posts");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    // ✅ Delete post (only owner allowed)
    @Transactional
    public void deletePost(Long postId) {

        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        // ✅ Ownership check
        String ownerEmail = post.getUser().getEmail();
        if (!ownerEmail.equals(currentEmail)) {
            throw new AccessDeniedException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }
}
