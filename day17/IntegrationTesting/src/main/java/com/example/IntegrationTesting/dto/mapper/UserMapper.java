package com.example.IntegrationTesting.dto.mapper;

import com.example.IntegrationTesting.dto.responseDTO.UserDTO;
import com.example.IntegrationTesting.entity.User;
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
