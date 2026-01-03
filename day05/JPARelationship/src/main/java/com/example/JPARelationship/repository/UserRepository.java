package com.example.JPARelationship.repository;

import com.example.JPARelationship.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
