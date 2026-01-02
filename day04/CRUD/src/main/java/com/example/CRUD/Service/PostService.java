package com.example.CRUD.Service;

import com.example.CRUD.Model.Post;
import com.example.CRUD.Repository.PostRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    public Post updatePost(Long id, Post updatedPost) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        existing.setTitle(updatedPost.getTitle());
        existing.setContent(updatedPost.getContent());
        existing.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(existing);
    }
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
