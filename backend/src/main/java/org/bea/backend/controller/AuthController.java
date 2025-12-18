package org.bea.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import org.bea.backend.model.User;
import org.bea.backend.service.CustomOAuth2UserService;

@RestController
@RequestMapping("eyf/login")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final CustomOAuth2UserService customOAuth2UserService;

    public AuthController(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @GetMapping("/success")
    public User getLoginInfo(Authentication authentication){
        String principal = authentication == null ? "null" : authentication.getName();
        log.info("Request GET /eyf/login/success - principal={}", principal);
        User user = customOAuth2UserService.getUser(authentication);
        log.debug("Resolved user: {}", user == null ? "null" : user.getEmail());
        return user;
    }
}
