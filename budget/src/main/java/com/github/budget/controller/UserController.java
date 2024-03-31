package com.github.budget.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.budget.entity.User;
import com.github.budget.service.UserService;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController()
class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // @PostMapping("/users")
    // public ResponseEntity<User> registerUser(@RequestBody User user) {
    // User newUser = userService.registerUser(user);
    // return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    // }
}
