package com.example.JWTAuthentication.dto.mapper;

import com.example.JWTAuthentication.dto.responseDTO.PostDTO;
import com.example.JWTAuthentication.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDto(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());

        if (post.getUser() != null) {
            dto.setOwnerEmail(post.getUser().getEmail());
        }

        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }
}
