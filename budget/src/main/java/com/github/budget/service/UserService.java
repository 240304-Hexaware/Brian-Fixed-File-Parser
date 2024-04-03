package com.github.budget.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.budget.entity.User;
import com.github.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

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

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            throw new UsernameNotFoundException("No users found");
        } else {
            return users;
        }
    }

    public User createUser(User newUser) {
        String username = newUser.getUsername();
        if (userRepository.existsByUsername(username) == false) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setRole("user");

            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User already exists");
        }
    }

    public User updateUser(User user) {
        String username = user.getUsername();
        if (userRepository.existsByUsername(username) == true) {
            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public User deleteUser(User user) {
        String username = user.getUsername();
        if (userRepository.existsByUsername(username) == true) {
            userRepository.delete(user);
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
