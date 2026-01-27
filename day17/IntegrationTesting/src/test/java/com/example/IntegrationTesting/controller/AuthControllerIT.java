package com.example.IntegrationTesting.controller;

import com.example.IntegrationTesting.dto.requestDTO.LoginRequest;
import com.example.IntegrationTesting.dto.requestDTO.RegisterRequest;
import com.example.IntegrationTesting.dto.responseDTO.AuthResponse;
import com.example.IntegrationTesting.dto.responseDTO.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthControllerIT {

    @LocalServerPort
    private int port;

    private RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @DisplayName("POST /api/auth/register should create user (201)")
    void register_shouldCreateUser() {

        String email = "testuser" + System.currentTimeMillis() + "@gmail.com";

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser"); // ✅ FIX
        request.setEmail(email);
        request.setPassword("123456");

        UserDTO response = restClient()
                .post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(UserDTO.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(email, response.getEmail());
    }

    @Test
    @DisplayName("POST /api/auth/login should return token (200)")
    void login_shouldReturnToken() {

        String email = "loginuser" + System.currentTimeMillis() + "@gmail.com";

        // ✅ register first
        RegisterRequest register = new RegisterRequest();
        register.setUsername("loginuser"); // ✅ FIX
        register.setEmail(email);
        register.setPassword("123456");

        restClient()
                .post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(register)
                .retrieve()
                .toBodilessEntity();

        // ✅ login
        LoginRequest login = new LoginRequest();
        login.setEmail(email);
        login.setPassword("123456");

        AuthResponse loginResponse = restClient()
                .post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(login)
                .retrieve()
                .body(AuthResponse.class);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());
    }

    @Test
    @DisplayName("Full auth flow: register -> login -> /me")
    void authFlow_registerLoginMe_shouldWork() {

        String email = "flow" + System.currentTimeMillis() + "@gmail.com";

        // ✅ register
        RegisterRequest register = new RegisterRequest();
        register.setUsername("flowuser"); // ✅ FIX
        register.setEmail(email);
        register.setPassword("123456");

        restClient()
                .post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(register)
                .retrieve()
                .toBodilessEntity();

        // ✅ login
        LoginRequest login = new LoginRequest();
        login.setEmail(email);
        login.setPassword("123456");

        AuthResponse loginResponse = restClient()
                .post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(login)
                .retrieve()
                .body(AuthResponse.class);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());

        String token = loginResponse.getToken();

        // ✅ /me call
        UserDTO me = restClient()
                .method(HttpMethod.GET)
                .uri("/api/auth/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(UserDTO.class);

        assertNotNull(me);
        assertEquals(email, me.getEmail());
    }
}
