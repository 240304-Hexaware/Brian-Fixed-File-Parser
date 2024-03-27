package com.github.budget.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.github.budget.domain.User;
import com.github.budget.repository.UserRepository;

@Component
public class UserInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @jakarta.annotation.PostConstruct
    public void initData() {
        // Check if the admin user already exists to avoid creating multiple admin users
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
