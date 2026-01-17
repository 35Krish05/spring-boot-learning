package com.example.DTO.service;


import com.example.DTO.dto.mapper.PostMapper;
import com.example.DTO.dto.requestDTO.CreatePostRequest;
import com.example.DTO.dto.responseDTO.PostDTO;
import com.example.DTO.entity.Post;
import com.example.DTO.entity.User;
import com.example.DTO.exception.ResourceNotFoundException;
import com.example.DTO.repository.PostRepository;
import com.example.DTO.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostDTO createPost(CreatePostRequest request) {

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id=" + request.getAuthorId()));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);

        Post saved = postRepository.save(post);

        return PostMapper.toDto(saved);
    }
}
