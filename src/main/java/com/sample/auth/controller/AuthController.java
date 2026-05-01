package com.sample.auth.controller;

import com.sample.auth.module.User;
import com.sample.auth.module.UserPrincipal;
import com.sample.auth.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public Map<String, Object> profile(Authentication authentication) {
        UserPrincipal authUser = (UserPrincipal) authentication.getPrincipal();

        User user = userService.loadUserByEmail(authUser.getUser().getEmail());

        return Map.of(
                "name", user.getFullName(),
                "email", user.getEmail(),
                "picture", user.getProfilePictureUrl()
        );
    }
}
