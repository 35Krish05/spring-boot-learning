package com.example.JUnit.service;

import com.example.JUnit.entity.User;
import com.example.JUnit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())       // ✅ email = username
                .password(user.getPassword())    // ✅ hashed password in DB
                .roles("USER")                   // ✅ default role
                .build();
    }
}
