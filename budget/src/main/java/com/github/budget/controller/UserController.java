package com.github.budget.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users")
    public ResponseEntity<User> deleteUser(User user) {
        User deletedUser = userService.deleteUser(user);
        return ResponseEntity.ok(deletedUser);
    }
}
