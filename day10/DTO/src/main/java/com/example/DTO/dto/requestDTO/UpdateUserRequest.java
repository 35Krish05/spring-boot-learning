package com.example.DTO.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(min = 3, max = 50, message = "username must be 3-50 characters")
    private String username;

    @Email(message = "email must be valid")
    private String email;

    // password optional update
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
