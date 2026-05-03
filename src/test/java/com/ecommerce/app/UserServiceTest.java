package com.ecommerce.app;

import com.ecommerce.app.dto.LoginRequest;
import com.ecommerce.app.dto.LoginResponse;
import com.ecommerce.app.dto.RegisterRequest;
import com.ecommerce.app.dto.RegisterResponse;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.security.JwtUtils;
import com.ecommerce.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserService
 */
@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    public void testUserRegistration() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("Test");
        request.setLastName("User");
        
        RegisterResponse response = userService.registerUser(request);
        
        assertNotNull(response);
        assertEquals("User registered successfully", response.getMessage());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
    }
    
    @Test
    public void testDuplicateUsernameRegistration() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test1@example.com");
        request.setPassword("password123");
        request.setFirstName("Test");
        request.setLastName("User");
        
        userService.registerUser(request);
        
        RegisterRequest duplicateRequest = new RegisterRequest();
        duplicateRequest.setUsername("testuser");
        duplicateRequest.setEmail("test2@example.com");
        duplicateRequest.setPassword("password123");
        duplicateRequest.setFirstName("Test");
        duplicateRequest.setLastName("User");
        
        assertThrows(Exception.class, () -> userService.registerUser(duplicateRequest));
    }
    
    @Test
    public void testUserLogin() throws Exception {
        // Register user first
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");
        
        userService.registerUser(registerRequest);
        
        // Test login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");
        
        LoginResponse response = userService.loginUser(loginRequest);
        
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
    }
    
    @Test
    public void testInvalidPasswordLogin() throws Exception {
        // Register user first
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");
        
        userService.registerUser(registerRequest);
        
        // Test login with wrong password
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");
        
        assertThrows(Exception.class, () -> userService.loginUser(loginRequest));
    }
    
    @Test
    public void testJwtTokenGeneration() {
        String token = jwtUtils.generateToken("testuser");
        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token));
        assertEquals("testuser", jwtUtils.getUsernameFromToken(token));
    }
}
