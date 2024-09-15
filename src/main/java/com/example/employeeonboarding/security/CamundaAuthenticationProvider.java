package com.example.employeeonboarding.security;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CamundaAuthenticationProvider implements AuthenticationProvider {

    private final IdentityService identityService;

    public CamundaAuthenticationProvider(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("Attempting to authenticate user: " + username);

        User user = identityService.createUserQuery().userId(username).singleResult();
        if (user == null) {
            System.out.println("User not found: " + username);
            throw new BadCredentialsException("User not found: " + username);
        }

        if (!identityService.checkPassword(username, password)) {
            System.out.println("Invalid password for user: " + username);
            throw new BadCredentialsException("Invalid password");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        identityService.createGroupQuery().groupMember(username).list().forEach(group -> {
            String authority = "ROLE_" + group.getId().toUpperCase();
            authorities.add(new SimpleGrantedAuthority(authority));
            System.out.println("Added authority for user " + username + ": " + authority);
        });

        System.out.println("Authentication successful for user: " + username);
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}