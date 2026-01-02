package com.example.CRUD.Controller;

    import com.example.CRUD.Model.User;
import com.example.CRUD.Service.UserService;
import jakarta.validation.Valid;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/users")
    public class PostController {

        private final UserService userService;

        public PostController(UserService userService) {
            this.userService = userService;
        }
        @PostMapping
        public User createUser(@Valid @RequestBody User user) {
            return userService.createUser(user);
        }
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(
                @PathVariable Long id,
                @RequestBody User user) {

            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }

    }
