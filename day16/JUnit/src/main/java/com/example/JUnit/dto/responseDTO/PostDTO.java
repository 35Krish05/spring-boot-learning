package com.example.JUnit.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private String title;
    private String content;

    private String ownerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
