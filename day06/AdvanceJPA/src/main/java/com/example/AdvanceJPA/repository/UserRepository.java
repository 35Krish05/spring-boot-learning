package com.example.AdvanceJPA.repository;

import com.example.AdvanceJPA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}