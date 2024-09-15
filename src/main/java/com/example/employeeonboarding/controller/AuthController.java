package com.example.employeeonboarding.controller;

import com.example.employeeonboarding.dto.LoginRequest;
import com.example.employeeonboarding.security.CamundaAuthenticationProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CamundaAuthenticationProvider authenticationProvider;

    public AuthController(CamundaAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login request received for user: " + loginRequest.getUsername());
        try {
            Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User logged in successfully");
            System.out.println("Login successful for user: " + loginRequest.getUsername());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            System.out.println("Login failed for user: " + loginRequest.getUsername() + ". Reason: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}