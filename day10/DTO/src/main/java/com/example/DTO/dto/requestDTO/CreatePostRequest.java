package com.example.DTO.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {

    @NotBlank(message = "title is required")
    @Size(max = 200, message = "title must be <= 200 characters")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    @NotNull(message = "authorId is required")
    private Long authorId;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Long getAuthorId() { return authorId; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
