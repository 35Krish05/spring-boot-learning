package com.example.CRUD.Service;


import com.example.CRUD.EmailAlreadyExistsException;
import com.example.CRUD.Model.User;
import com.example.CRUD.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
    public class UserService {

        private final PostRepository userRepository;

        public UserService(PostRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User createUser(User user) throws EmailAlreadyExistsException {

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists");
            }


            user.setCreatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        public User updateUser(Long id, User updatedUser) {

            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            return userRepository.save(existingUser);
        }
        public void deleteUser(Long id) {

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userRepository.delete(user);
        }


    }
