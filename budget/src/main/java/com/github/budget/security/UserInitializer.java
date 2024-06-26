package com.github.budget.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.budget.entity.User;
import com.github.budget.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserInitializer {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @jakarta.annotation.PostConstruct
    public void initData() {
        String adminUsername = "admin";
        if (userRepository.existsByUsername(adminUsername) == false) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode("adminpassword"));
            adminUser.setRole("ROLE_ADMIN");

            userRepository.save(adminUser);
        }
    }
}
