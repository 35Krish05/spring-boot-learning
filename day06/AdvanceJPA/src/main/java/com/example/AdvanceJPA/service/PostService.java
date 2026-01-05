package com.example.AdvanceJPA.service;

import com.example.AdvanceJPA.InvalidDaysException;
import com.example.AdvanceJPA.InvalidSearchKeywordException;
import com.example.AdvanceJPA.ResourceNotFoundException;
import com.example.AdvanceJPA.entity.Post;
import com.example.AdvanceJPA.entity.User;
import com.example.AdvanceJPA.repository.PostRepository;
import com.example.AdvanceJPA.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> findPostsByUserId(@Param("userId") Long userId){
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId
            );
        }

        return postRepository.findPostsByUserId(userId);
    }


   public List<Post> searchPostsByTitle(String keyword) {
       if (keyword == null || keyword.trim().isEmpty()) {
           throw new InvalidSearchKeywordException("Search keyword cannot be empty");
       }
       return postRepository.findPostsByTitleKeyword(keyword);
   }

    public Page<Post> getRecentPosts(Pageable pageable,int days) {
        if (days <= 0 || days > 365) {
            throw new InvalidDaysException("Days must be Valid");
        }

        LocalDateTime daysAgo = LocalDateTime.now().minusDays(days);
        return postRepository.findRecentPosts(daysAgo, pageable);
    }
}