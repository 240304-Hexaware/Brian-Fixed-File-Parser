package com.github.budget.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.budget.dto.request.UserRequestDto;
import com.github.budget.dto.response.AdminUserResponseDto;
import com.github.budget.dto.response.UserResponseDto;
import com.github.budget.entity.User;
import com.github.budget.mapper.UserMapper;
import com.github.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        getAuthorities(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        // Add more logic here if you're handling multiple roles or permissions.
        return authorities;
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::mapToUserResponseDto).collect(Collectors.toList());
    }

    public void updateUser(String username, UserRequestDto newUser) {
        User user = UserMapper.mapToUserWithRole(newUser);

        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        existingUser.get().setRole(user.getRole());

        userRepository.save(existingUser.get());
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }

    public AdminUserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserMapper.mapToAdminUserResponseDto(user);
    }
}
