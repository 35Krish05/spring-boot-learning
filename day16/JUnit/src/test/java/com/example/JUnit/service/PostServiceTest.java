package com.example.JUnit.service;

import com.example.JUnit.dto.requestDTO.PostRequest;
import com.example.JUnit.entity.Post;
import com.example.JUnit.entity.User;
import com.example.JUnit.repository.PostRepository;
import com.example.JUnit.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    // 🔥 Security mocks
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setupSecurityContext() {
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    // ✅ Helper: mock logged in email only when required
    private void mockLoggedInUser(String email) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
    }

    @Test
    @DisplayName("createPost() should create post and assign logged-in user as owner")
    void createPost_shouldAssignOwnerAndSave() {
        // Arrange
        String currentEmail = "user@gmail.com";
        mockLoggedInUser(currentEmail);

        User user = new User();
        user.setId(1L);
        user.setEmail(currentEmail);

        when(userRepository.findByEmail(currentEmail)).thenReturn(Optional.of(user));

        PostRequest request = new PostRequest();
        request.setTitle("My Title");
        request.setContent("My Content");

        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Post result = postService.createPost(request);

        // Assert
        assertNotNull(result);
        assertEquals("My Title", result.getTitle());
        assertEquals("My Content", result.getContent());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        assertNotNull(result.getUser());
        assertEquals(currentEmail, result.getUser().getEmail());

        verify(userRepository, times(1)).findByEmail(currentEmail);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("createPost() should throw exception when logged-in user not found in DB")
    void createPost_shouldThrowWhenUserNotFound() {
        // Arrange
        String currentEmail = "missing@gmail.com";
        mockLoggedInUser(currentEmail);

        when(userRepository.findByEmail(currentEmail)).thenReturn(Optional.empty());

        PostRequest request = new PostRequest();
        request.setTitle("Hello");
        request.setContent("Content");

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> postService.createPost(request));

        assertEquals("User not found: " + currentEmail, ex.getMessage());

        verify(userRepository, times(1)).findByEmail(currentEmail);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("getAllPosts() should return all posts")
    void getAllPosts_shouldReturnAllPosts() {
        // Arrange
        Post p1 = new Post();
        p1.setId(1L);
        p1.setTitle("Post 1");

        Post p2 = new Post();
        p2.setId(2L);
        p2.setTitle("Post 2");

        when(postRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<Post> result = postService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("updatePost() should update post when logged-in user is owner")
    void updatePost_shouldUpdateWhenOwner() {
        // Arrange
        String currentEmail = "owner@gmail.com";
        mockLoggedInUser(currentEmail);

        User owner = new User();
        owner.setId(1L);
        owner.setEmail(currentEmail);

        Post post = new Post();
        post.setId(100L);
        post.setTitle("Old");
        post.setContent("Old Content");
        post.setUser(owner);

        when(postRepository.findById(100L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostRequest request = new PostRequest();
        request.setTitle("New Title");
        request.setContent("New Content");

        // Act
        Post result = postService.updatePost(100L, request);

        // Assert
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
        assertNotNull(result.getUpdatedAt());

        verify(postRepository, times(1)).findById(100L);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("updatePost() should throw AccessDeniedException when logged-in user is not owner")
    void updatePost_shouldThrowWhenNotOwner() {
        // Arrange
        String currentEmail = "hacker@gmail.com";
        mockLoggedInUser(currentEmail);

        User owner = new User();
        owner.setId(1L);
        owner.setEmail("owner@gmail.com");

        Post post = new Post();
        post.setId(100L);
        post.setUser(owner);

        when(postRepository.findById(100L)).thenReturn(Optional.of(post));

        PostRequest request = new PostRequest();
        request.setTitle("Hack Title");
        request.setContent("Hack Content");

        // Act + Assert
        AccessDeniedException ex = assertThrows(AccessDeniedException.class,
                () -> postService.updatePost(100L, request));

        assertEquals("You can only edit your own posts", ex.getMessage());

        verify(postRepository, times(1)).findById(100L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("updatePost() should throw exception when post not found")
    void updatePost_shouldThrowWhenPostNotFound() {
        // Arrange
        String currentEmail = "owner@gmail.com";
        mockLoggedInUser(currentEmail);

        when(postRepository.findById(404L)).thenReturn(Optional.empty());

        PostRequest request = new PostRequest();
        request.setTitle("New");
        request.setContent("New");

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> postService.updatePost(404L, request));

        assertEquals("Post not found: 404", ex.getMessage());

        verify(postRepository, times(1)).findById(404L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("deletePost() should delete post when logged-in user is owner")
    void deletePost_shouldDeleteWhenOwner() {
        // Arrange
        String currentEmail = "owner@gmail.com";
        mockLoggedInUser(currentEmail);

        User owner = new User();
        owner.setEmail(currentEmail);

        Post post = new Post();
        post.setId(99L);
        post.setUser(owner);

        when(postRepository.findById(99L)).thenReturn(Optional.of(post));

        // Act
        postService.deletePost(99L);

        // Assert
        verify(postRepository, times(1)).findById(99L);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    @DisplayName("deletePost() should throw AccessDeniedException when logged-in user is not owner")
    void deletePost_shouldThrowWhenNotOwner() {
        // Arrange
        String currentEmail = "someone@gmail.com";
        mockLoggedInUser(currentEmail);

        User owner = new User();
        owner.setEmail("owner@gmail.com");

        Post post = new Post();
        post.setId(99L);
        post.setUser(owner);

        when(postRepository.findById(99L)).thenReturn(Optional.of(post));

        // Act + Assert
        AccessDeniedException ex = assertThrows(AccessDeniedException.class,
                () -> postService.deletePost(99L));

        assertEquals("You can only delete your own posts", ex.getMessage());

        verify(postRepository, times(1)).findById(99L);
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    @DisplayName("deletePost() should throw exception when post not found")
    void deletePost_shouldThrowWhenPostNotFound() {
        // Arrange
        String currentEmail = "owner@gmail.com";
        mockLoggedInUser(currentEmail);

        when(postRepository.findById(404L)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> postService.deletePost(404L));

        assertEquals("Post not found: 404", ex.getMessage());

        verify(postRepository, times(1)).findById(404L);
        verify(postRepository, never()).delete(any(Post.class));
    }
}
