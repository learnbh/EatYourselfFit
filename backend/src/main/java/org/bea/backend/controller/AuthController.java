package org.bea.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import org.bea.backend.model.User;
import org.bea.backend.service.CustomOAuth2UserService;

@RestController
@RequestMapping("eyf/login")
public class AuthController {

    private final CustomOAuth2UserService customOAuth2UserService;

    public AuthController(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @GetMapping("/success")
    public User getLoginInfo(Authentication authentication){
        return customOAuth2UserService.getUser(authentication);
    }
}
