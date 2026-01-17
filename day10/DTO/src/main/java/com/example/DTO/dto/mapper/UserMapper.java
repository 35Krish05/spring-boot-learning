package com.example.DTO.dto.mapper;

import com.example.DTO.dto.responseDTO.UserDTO;
import com.example.DTO.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
