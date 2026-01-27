package com.example.IntegrationTesting.controller;

import com.example.IntegrationTesting.dto.mapper.UserMapper;
import com.example.IntegrationTesting.dto.requestDTO.LoginRequest;
import com.example.IntegrationTesting.dto.requestDTO.RegisterRequest;
import com.example.IntegrationTesting.dto.responseDTO.AuthResponse;
import com.example.IntegrationTesting.dto.responseDTO.UserDTO;
import com.example.IntegrationTesting.entity.User;
import com.example.IntegrationTesting.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    @GetMapping("/me")
    public UserDTO me() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // ✅ token subject (email)

        User user = authService.getCurrentUser(email);
        return userMapper.toDto(user);
    }

}
