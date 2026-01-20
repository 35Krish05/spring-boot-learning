package com.example.JWTAuthentication.service;



import com.example.JWTAuthentication.dto.requestDTO.LoginRequest;
import com.example.JWTAuthentication.dto.requestDTO.RegisterRequest;
import com.example.JWTAuthentication.entity.User;
import com.example.JWTAuthentication.exception.EmailAlreadyExistsException;
import com.example.JWTAuthentication.repository.UserRepository;
import com.example.JWTAuthentication.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // ✅ Register user
    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered: " + request.getEmail()
            );
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // ✅ Login user + generate token
    public String login(LoginRequest request) {

        // This line will trigger:
        // CustomUserDetailsService -> DB -> PasswordEncoder -> Authentication success/fail
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // if authentication success -> generate JWT
        return jwtUtil.generateToken(request.getEmail());
    }
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}
