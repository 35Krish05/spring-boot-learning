package com.example.JPA.Service;

import com.example.JPA.EmailAlreadyExistsException;
import com.example.JPA.Model.User;
import com.example.JPA.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }


        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
