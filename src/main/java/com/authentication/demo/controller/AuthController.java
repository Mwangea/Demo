package com.authentication.demo.controller;

import com.authentication.demo.entity.User;
import com.authentication.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signup(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        String fullName = principal.getAttribute("name");
        String providerId = principal.getAttribute("sub");

        log.info("Google OAuth2 Authentication Successful");
        log.info("Email: {}", email);
        log.info("Full Name: {}", fullName);
        log.info("Provider ID: {}", providerId);

        return "Signup successful. User logged: " + fullName + " (" + email + ")";
    }

    @PostMapping("/create")
    public String createUser(@AuthenticationPrincipal OAuth2User principal) {
        String providerId = principal.getAttribute("sub");

        if (!userRepository.existsByProviderId(providerId)) {
            User user = new User();
            user.setAuthProvider("google");
            user.setProviderId(providerId);
            userRepository.save(user);

            log.info("User record created with provider ID: {}", providerId);
            return "User record created successfully (without personal data)";
        }
        return "User already exists";
    }
}