package com.example.DTO.dto.responseDTO;

public class UserSummaryDTO {

    private Long id;
    private String username;

    public UserSummaryDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
}
