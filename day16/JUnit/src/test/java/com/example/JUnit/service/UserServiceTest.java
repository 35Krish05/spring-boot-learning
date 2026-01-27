package com.example.JUnit.service;

import com.example.JUnit.entity.User;
import com.example.JUnit.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("getAllUsers() should return list of users from repository")
    void getAllUsers_shouldReturnUsers() {
        // Arrange
        User u1 = new User();
        u1.setId(1L);
        u1.setEmail("a@gmail.com");

        User u2 = new User();
        u2.setId(2L);
        u2.setEmail("b@gmail.com");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("a@gmail.com", result.get(0).getEmail());
        assertEquals("b@gmail.com", result.get(1).getEmail());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("deleteUserById() should delete user when id exists")
    void deleteUserById_shouldDeleteWhenUserExists() {
        // Arrange
        Long id = 10L;

        when(userRepository.existsById(id)).thenReturn(true);

        // Act
        userService.deleteUserById(id);

        // Assert
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deleteUserById() should throw exception when user does not exist")
    void deleteUserById_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long id = 999L;

        when(userRepository.existsById(id)).thenReturn(false);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.deleteUserById(id));

        assertEquals("User not found: " + id, ex.getMessage());

        verify(userRepository, times(1)).existsById(id);

        // important: delete should NOT happen
        verify(userRepository, never()).deleteById(anyLong());
    }
}
