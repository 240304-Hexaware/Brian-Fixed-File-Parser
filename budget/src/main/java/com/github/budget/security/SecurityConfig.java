package com.github.budget.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.github.budget.security.filters.JWTTokenGeneratorFilter;
import com.github.budget.security.filters.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .cors(corsCustomizer -> corsCustomizer
                                                .configurationSource(new CorsConfigurationSource() {
                                                        @Override
                                                        public CorsConfiguration getCorsConfiguration(
                                                                        HttpServletRequest request) {
                                                                CorsConfiguration config = new CorsConfiguration();
                                                                config.setAllowedOrigins(Collections.singletonList(
                                                                                "http://localhost:4200"));
                                                                config.setAllowedMethods(
                                                                                Collections.singletonList("*"));
                                                                config.setAllowCredentials(true);
                                                                config.setAllowedHeaders(
                                                                                Collections.singletonList("*"));
                                                                config.setExposedHeaders(
                                                                                Arrays.asList("*"));
                                                                config.setMaxAge(3600L);
                                                                return config;
                                                        }
                                                }))
                                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                                .authorizeHttpRequests(authorize -> {
                                        authorize.requestMatchers("/").permitAll();
                                        authorize.requestMatchers("/error").permitAll();
                                        authorize.requestMatchers("/signin").permitAll();
                                        authorize.requestMatchers("/register").permitAll();
                                        authorize.requestMatchers("/users").hasRole("ADMIN");
                                        authorize.anyRequest().authenticated();
                                })
                                .formLogin(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
