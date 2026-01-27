package com.example.IntegrationTesting.controller;

import com.example.IntegrationTesting.dto.responseDTO.UserDTO;
import com.example.IntegrationTesting.dto.mapper.UserMapper;
import com.example.IntegrationTesting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;
    private final UserMapper userMapper;

    // ✅ GET /api/users  (ADMIN only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // ✅ DELETE /api/users/{id}  (ADMIN only)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
