package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.entity.User;
import com.github.budget.repository.UserRepository;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
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

    @PostMapping("/signin")
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
