package com.example.Registration.dto.mapper;

import com.example.Registration.dto.responseDTO.UserDTO;
import com.example.Registration.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);
}
