package com.agrotical.service;

import com.agrotical.entity.Field;
import com.agrotical.entity.User;
import com.agrotical.repository.FieldRepository;
import com.agrotical.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FieldServiceTest {

    private FieldService fieldService;
    private UserRepository userRepository;
    private FieldRepository fieldRepository;

    @BeforeEach
    void setUp() {
    userRepository = mock(UserRepository.class);
    fieldRepository = mock(FieldRepository.class);
    fieldService = new FieldService(fieldRepository, userRepository);
    }


    @Test
    void create_whenUserExists() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Field field = new Field();
        field.setName("Χωράφι 1");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(fieldRepository.save(any(Field.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Field savedField = fieldService.createField(userId, field);

        assertNotNull(savedField);
        assertEquals("Χωράφι 1", savedField.getName());
        assertEquals(user, savedField.getUser());

        verify(userRepository).findById(userId);
        verify(fieldRepository).save(field);
    }

    @Test
    void create_whenUserMissing() {
        Long userId = 999L;
        Field field = new Field();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fieldService.createField(userId, field)
        );

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(fieldRepository, never()).save(any());
    }
}
