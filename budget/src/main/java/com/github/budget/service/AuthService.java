package com.github.budget.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.budget.entity.User;
import com.github.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public ResponseEntity<User> registerUser(User user) {

        ResponseEntity<User> response = null;
        try {
            if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                User newUser = new User();
                newUser.setUsername(user.getUsername());
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                newUser.setRole("ROLE_USER");

                userRepository.save(newUser);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(newUser);
            }
        } catch (Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
        return response;
    }

    public ResponseEntity<User> loginUser(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        ResponseEntity<User> response = null;
        if (user.isPresent()) {
            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.get());
            return response;
        } else {
            return null;
        }
    }

}
