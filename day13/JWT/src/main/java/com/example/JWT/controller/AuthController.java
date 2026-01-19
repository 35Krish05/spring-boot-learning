package com.example.JWT.controller;

import com.example.JWT.dto.mapper.UserMapper;
import com.example.JWT.dto.requestDTO.LoginRequest;
import com.example.JWT.dto.requestDTO.RegisterRequest;
import com.example.JWT.dto.responseDTO.AuthResponse;
import com.example.JWT.dto.responseDTO.UserDTO;
import com.example.JWT.entity.User;
import com.example.JWT.service.AuthService;
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

    // ✅ Register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        // return safe DTO (no password)
        return userMapper.toDto(user);
    }

    // ✅ Login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return new AuthResponse(token);
    }
}
