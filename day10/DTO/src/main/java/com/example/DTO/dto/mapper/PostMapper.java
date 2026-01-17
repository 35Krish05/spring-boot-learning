package com.example.DTO.dto.mapper;

import com.example.DTO.dto.responseDTO.PostDTO;
import com.example.DTO.dto.responseDTO.UserSummaryDTO;
import com.example.DTO.entity.Post;

public class PostMapper {

    private PostMapper() {}

    public static PostDTO toDto(Post post) {

        UserSummaryDTO author = new UserSummaryDTO(
                post.getAuthor().getId(),
                post.getAuthor().getUsername()
        );

        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                author,
                post.getCreatedAt()
        );
    }
}
