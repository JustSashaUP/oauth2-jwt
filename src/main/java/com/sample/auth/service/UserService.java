package com.sample.auth.service;

import com.sample.auth.module.User;
import com.sample.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    public User findOrCreateUser(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(new User(email)));
        } catch(DataIntegrityViolationException ex) {
            throw new RuntimeException("Data constrain violation while save user.");
        }
    }
}
