package com.example.employeeonboarding.config;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class IdentityServiceConfig {

    @Autowired
    private IdentityService identityService;

    @PostConstruct
    public void initializeUsers() {
        String rawPassword = "password";
        createUserIfNotExists("john", "John", "Doe", rawPassword);

        if(identityService.createGroupQuery().groupId("onboarding").singleResult() == null) {
            Group group = identityService.newGroup("onboarding");
            group.setName("Onboarding Managers");
            group.setType("WORKFLOW");
            identityService.saveGroup(group);
        }

        identityService.createMembership("john", "onboarding");

        // Verify stored password
        User user = identityService.createUserQuery().userId("john").singleResult();
        if (user != null) {
            System.out.println("User 'john' created successfully");
            System.out.println("Verifying password: " + identityService.checkPassword("john", rawPassword));
        }
    }

    private void createUserIfNotExists(String userId, String firstName, String lastName, String password) {
        User existingUser = identityService.createUserQuery().userId(userId).singleResult();
                
        if (existingUser == null) {
            User user = identityService.newUser(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            identityService.saveUser(user);
            System.out.println("Created user: " + userId);
        } else {
            existingUser.setFirstName(firstName);
            existingUser.setLastName(lastName);
            existingUser.setPassword(password);
            identityService.saveUser(existingUser);
            System.out.println("Updated user: " + userId);
        }
    }
}