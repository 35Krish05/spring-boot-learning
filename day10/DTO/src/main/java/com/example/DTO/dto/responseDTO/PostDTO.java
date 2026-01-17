package com.example.DTO.dto.responseDTO;


import java.time.LocalDateTime;

public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private UserSummaryDTO author;
    private LocalDateTime createdAt;

    public PostDTO(Long id, String title, String content, UserSummaryDTO author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public UserSummaryDTO getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
