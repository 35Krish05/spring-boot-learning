package com.example.IntegrationTesting.repository;

import com.example.IntegrationTesting.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
