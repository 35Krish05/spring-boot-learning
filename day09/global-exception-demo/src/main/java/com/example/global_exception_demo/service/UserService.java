package com.example.global_exception_demo.service;

import com.example.global_exception_demo.dto.UserCreateRequest;
import com.example.global_exception_demo.exception.BadRequestException;
import com.example.global_exception_demo.exception.DuplicateResourceException;
import com.example.global_exception_demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    // fake email store
    private final Set<String> existingEmails = Set.of("test@gmail.com", "rahul@gmail.com");

    public String getUserById(Long id) {
        if (id != 1) {
            throw new ResourceNotFoundException("User not found with id=" + id);
        }
        return "User#1";
    }

    public String createUser(UserCreateRequest request) {

        // Business validation example
        if (request.getUsername().equalsIgnoreCase("admin")) {
            throw new BadRequestException("username 'admin' is not allowed");
        }

        // Duplicate resource scenario
        if (existingEmails.contains(request.getEmail().toLowerCase())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        return "User created: " + request.getUsername();
    }
}
