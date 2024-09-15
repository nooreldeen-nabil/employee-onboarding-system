package com.example.employeeonboarding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String to, String subject, String body) {
        // In a real application, this would send an actual email
        logger.info("Sending email to: {}", to);
        logger.debug("Email subject: {}", subject);
        logger.debug("Email body: {}", body);
        // Implement email sending logic here
    }

    public void sendSMS(String phoneNumber, String message) {
        // In a real application, this would send an actual SMS
        logger.info("Sending SMS to: {}", phoneNumber);
        logger.debug("SMS message: {}", message);
        // Implement SMS sending logic here
    }

    public void notifyHR(String employeeId, String status) {
        String subject = "Employee Onboarding Update";
        String body = String.format("Employee %s onboarding status has been updated to: %s", employeeId, status);
        sendEmail("hr@company.com", subject, body);
    }

    public void notifyManager(String employeeId, String managerEmail, String status) {
        String subject = "New Employee Onboarding Update";
        String body = String.format("Your new team member (Employee ID: %s) onboarding status has been updated to: %s", employeeId, status);
        sendEmail(managerEmail, subject, body);
    }

    public void sendWelcomeMessage(String employeeId, String employeeEmail) {
        String subject = "Welcome to Our Company!";
        String body = "Welcome aboard! We're excited to have you join our team. Your onboarding process has begun, and you'll receive further instructions soon.";
        sendEmail(employeeEmail, subject, body);
    }
}