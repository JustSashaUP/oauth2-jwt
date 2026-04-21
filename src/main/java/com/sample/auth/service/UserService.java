package com.sample.auth.service;

import com.sample.auth.module.User;
import com.sample.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findOrCreateUser(User user) {
        try {
            return userRepository.findByEmail(user.email())
                    .orElseGet(() -> userRepository.save(user));
        } catch(DataIntegrityViolationException ex) {
            throw new RuntimeException("Data constrain violation while save user.");
        }
    }
}
