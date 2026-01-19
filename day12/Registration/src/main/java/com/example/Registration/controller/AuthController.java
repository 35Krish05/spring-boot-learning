package com.example.Registration.controller;

import com.example.Registration.dto.requestDTO.RegisterRequest;
import com.example.Registration.dto.responseDTO.UserDTO;
import com.example.Registration.dto.mapper.UserMapper;
import com.example.Registration.entity.User;
import com.example.Registration.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        // ✅ return safe DTO (no password)
        return userMapper.toDto(user);
    }
}
