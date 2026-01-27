package com.example.IntegrationTesting.service;

import com.example.IntegrationTesting.dto.requestDTO.LoginRequest;
import com.example.IntegrationTesting.dto.requestDTO.RegisterRequest;
import com.example.IntegrationTesting.entity.Role;
import com.example.IntegrationTesting.entity.User;
import com.example.IntegrationTesting.exception.EmailAlreadyExistsException;
import com.example.IntegrationTesting.repository.UserRepository;
import com.example.IntegrationTesting.security.JwtUtil;
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

        // ✅ default role
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // ✅ Login user + generate token (with role in claims)
    public String login(LoginRequest request) {

        // ✅ authentication trigger
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // ✅ DB se user nikaalo so we can fetch role
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));

        // ✅ generate JWT token with role claim
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // ✅ Current user details
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}
