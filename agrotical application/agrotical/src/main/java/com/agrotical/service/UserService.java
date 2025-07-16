package com.agrotical.service;

import com.agrotical.entity.User;
import com.agrotical.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository= userRepository;
    }

    public User registerUser(User user) {
    return userRepository.findByUsername(user.getUsername())
            .orElseGet(() -> userRepository.save(user));
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
    return userRepository.findAll();
    }

    public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
    }


}
