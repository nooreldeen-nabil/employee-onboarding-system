package com.example.employeeonboarding.config;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final IdentityService identityService;
    private final TaskService taskService;

    public SecurityConfig(IdentityService identityService, TaskService taskService) {
        this.identityService = identityService;
        this.taskService = taskService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/onboarding/start").hasRole("ONBOARDING")
                .antMatchers("/api/onboarding/tasks/{taskId}/**").access("hasRole('ONBOARDING') or @taskAuthorizationEvaluator.isAssignedOrCandidate(authentication, #taskId)")
                .antMatchers("/api/onboarding/process/**").hasAnyRole("ONBOARDING", "NEW_EMPLOYEE")
                .antMatchers("/api/onboarding/processes/completed").hasRole("ONBOARDING")
                .anyRequest().authenticated()
            .and()
            .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("Attempting to load user details for username: " + username);

            org.camunda.bpm.engine.identity.User camundaUser = identityService.createUserQuery()
                    .userId(username)
                    .singleResult();

            if (camundaUser != null) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                
                // Add roles based on Camunda groups
                identityService.createGroupQuery()
                        .groupMember(username)
                        .list()
                        .forEach(group -> {
                            String role = "ROLE_" + group.getId().toUpperCase();
                            System.out.println("Granting role " + role + " to user " + username);
                            authorities.add(new SimpleGrantedAuthority(role));
                        });

                // Add NEW_EMPLOYEE role if user has assigned tasks
                if (taskService.createTaskQuery().taskAssignee(username).count() > 0) {
                    String role = "ROLE_NEW_EMPLOYEE";
                    System.out.println("Granting role " + role + " to user " + username + " based on task assignment");
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                System.out.println("User " + username + " loaded successfully with roles: " + authorities);
                return new User(username, camundaUser.getPassword(), authorities);
            }

            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return sha512(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                System.out.println("Matching password for user");
                boolean matches = sha512(rawPassword.toString()).equals(encodedPassword);
                System.out.println("Password match result: " + matches);
                return matches;
            }
        };
    }

    private String sha512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digested = md.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(digested);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not found", e);
        }
    }
}