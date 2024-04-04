package com.github.budget.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.budget.dto.request.UserRequestDto;
import com.github.budget.dto.response.UserResponseDto;
import com.github.budget.entity.User;
import com.github.budget.exception.ResourceNotFoundException;
import com.github.budget.exception.UserAlreadyExistsException;
import com.github.budget.mapper.UserMapper;
import com.github.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public void registerUser(UserRequestDto userDto) {
        User user = UserMapper.mapToUser(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    public UserResponseDto loginUser(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "username", authentication.getName());
        }

        return UserMapper.mapToUserResponseDto(user.get());
    }

}
