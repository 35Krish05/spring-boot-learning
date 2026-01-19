package com.example.Registration.repository;

import com.example.Registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    // optional but useful
     Optional<User> findByEmail(String email);
}
