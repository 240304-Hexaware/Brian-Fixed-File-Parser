package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.constant.Constant;
import com.github.budget.dto.request.UserRequestDto;
import com.github.budget.dto.response.ResponseDto;
import com.github.budget.dto.response.UserResponseDto;
import com.github.budget.service.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class LoginController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserRequestDto userDto) {
        authService.registerUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_201));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> loginUser(Authentication authentication) {
        UserResponseDto user = authService.loginUser(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
