package com.agrotical.service;

import com.agrotical.entity.User;

import java.util.Collections;

public class UserFactory {

    
    public static User create(String username) {
        return new User.Builder()
                .username(username)
                .fields(Collections.emptyList())
                .build();
    }

    
    public static User create(String username, java.util.List<com.agrotical.entity.Field> fields) {
        return new User.Builder()
                .username(username)
                .fields(fields)
                .build();
    }
} 
