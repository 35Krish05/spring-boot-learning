package com.example.RestApiBasic.controller;

import com.example.RestApiBasic.Model.User;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
 /*   @GetMapping
    public String getAllUsers() {
        return "All users API working";
    }
    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id) {
        return "User id is " + id;
    }
    @GetMapping("/search")
    public String searchUser(@RequestParam String name) {
        return "Searching user: " + name;
    }
    @PostMapping
    public String createUser() {
        return "User created successfully";
    }

  */
 private List<User> users = new ArrayList<>();
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

        user.setId((long) (users.size() + 1));
        user.setCreatedAt(LocalDateTime.now());

        users.add(user);
        return user;
    }


}
