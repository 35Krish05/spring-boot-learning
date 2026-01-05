package com.example.AdvanceJPA.repository;


import com.example.AdvanceJPA.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}