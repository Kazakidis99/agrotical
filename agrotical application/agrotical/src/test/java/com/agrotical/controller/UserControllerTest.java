package com.agrotical.controller;

import com.agrotical.entity.User;
import com.agrotical.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void testLogin_whenUserExistsAndPasswordMatches_shouldReturnUserId() {
        User inputUser = new User();
        inputUser.setUsername("giorgos");
        inputUser.setPassword("1234");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("giorgos");
        existingUser.setPassword("1234");

        when(userService.findByUsername("giorgos"))
            .thenReturn(Optional.of(existingUser));

        ResponseEntity<?> response = userController.login(inputUser);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testLogin_whenUserDoesNotExist_shouldReturnUnauthorized() {
        User inputUser = new User();
        inputUser.setUsername("nonexistent");
        inputUser.setPassword("1234");

        when(userService.findByUsername("nonexistent"))
            .thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.login(inputUser);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testLogin_whenPasswordIsIncorrect_shouldReturnUnauthorized() {
        User inputUser = new User();
        inputUser.setUsername("giorgos");
        inputUser.setPassword("wrongpass");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("giorgos");
        existingUser.setPassword("correctpass");

        when(userService.findByUsername("giorgos"))
            .thenReturn(Optional.of(existingUser));

        ResponseEntity<?> response = userController.login(inputUser);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid username or password", response.getBody());
    }
}
