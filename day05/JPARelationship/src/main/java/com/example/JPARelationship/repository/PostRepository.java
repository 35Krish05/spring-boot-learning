package com.example.JPARelationship.repository;

import com.example.JPARelationship.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}