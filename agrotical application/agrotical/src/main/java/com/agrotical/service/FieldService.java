package com.agrotical.service;

import com.agrotical.entity.Field;
import com.agrotical.repository.FieldRepository;
import com.agrotical.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agrotical.entity.User;
import com.agrotical.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository, UserRepository userRepository) {
    this.fieldRepository = fieldRepository;
    this.userRepository = userRepository;
    }


    public Field createField(Long userId, Field field) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        field.setUser(user);
        return fieldRepository.save(field);
    }


    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public Optional<Field> getFieldById(Long id) {
        return fieldRepository.findById(id);
    }

    public List<Field> getFieldsByUserId(Long userId) {
        return fieldRepository.findByUser_Id(userId); 
    }

    
}
