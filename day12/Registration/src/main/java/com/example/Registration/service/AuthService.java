package com.example.Registration.service;

import com.example.Registration.dto.requestDTO.RegisterRequest;
import com.example.Registration.entity.User;
import com.example.Registration.exception.DuplicateResourceException;
import com.example.Registration.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        // ✅ business validation: duplicate email not allowed
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // ✅ IMPORTANT: hash password before saving
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }
}
