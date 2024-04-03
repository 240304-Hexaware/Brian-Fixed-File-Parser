package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.entity.User;
import com.github.budget.repository.UserRepository;
import com.github.budget.service.AuthService;

import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class LoginController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<User> loginUser(Authentication authentication) {
        return authService.loginUser(authentication);
    }
}
