package com.example.JUnit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // ✅ hashed password store hoga
    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;
}
