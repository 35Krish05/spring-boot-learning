package com.example.JPARelationship.repository;

import com.example.JPARelationship.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}