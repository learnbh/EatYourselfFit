package org.bea.backend.service;

import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.bea.backend.enums.UserTypes;
import org.bea.backend.exception.UserIllegalArgumentException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    private CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    public User insertUser(OAuth2User oauth2User, String provider) { 
        UserDto userDto = new UserDto(
            provider.equalsIgnoreCase(UserTypes.GOOGLE.name()) 
                ? oauth2User.getAttribute("name") 
                : oauth2User.getAttribute("login"),
            oauth2User.getAttribute("email"),
            provider.equalsIgnoreCase(UserTypes.GOOGLE.name()) 
                ? oauth2User.getAttribute("picture")
                : oauth2User.getAttribute("avatar_url"),
            provider.toUpperCase());
            return userService.insertUser(userDto, provider + "_" + oauth2User.getName());
    }

    public User getUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauth2) {
            String provider = oauth2.getAuthorizedClientRegistrationId();
            OAuth2User oauth2User = oauth2.getPrincipal();
            return userService
                .getUserById(provider + "_" + oauth2User.getName())
                .orElseGet(() -> insertUser(oauth2User, provider));
        } 
        throw new UserIllegalArgumentException("User konnte nicht geladen werden - Authentication is not of type OAuth2AuthenticationToken");
    }
}
