package com.example.JWTAuthentication.dto.mapper;

import com.example.JWTAuthentication.dto.responseDTO.UserDTO;
import com.example.JWTAuthentication.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
