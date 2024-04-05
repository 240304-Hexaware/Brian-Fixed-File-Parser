package com.github.budget.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.budget.constant.Constant;
import com.github.budget.dto.request.UserRequestDto;
import com.github.budget.dto.response.AdminUserResponseDto;
import com.github.budget.dto.response.ResponseDto;
import com.github.budget.dto.response.UserResponseDto;
import com.github.budget.entity.User;
import com.github.budget.service.UserService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController()
@AllArgsConstructor
@RequestMapping("/users")
class UserController {
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<AdminUserResponseDto> getUser(@PathVariable String username) {
        AdminUserResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable String username, @RequestBody UserRequestDto user) {
        userService.updateUser(username, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_201));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));
    }
}
