package org.bea.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.Optional;

import org.bea.backend.exception.UserIllegalArgumentException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserServiceTest {

    private UserService mockUserService;
    private OAuth2AuthenticationToken mockOAuth2AuthenticationToken;
    private Authentication mockAuthentication;
    private OAuth2User mockOAuth2User;

    private CustomOAuth2UserService customOAuth2UserService;
    
    private final Instant nowInstant = Instant.now();

    private User googleUser;
    private User githubUser;

    @BeforeEach
    void setUp() {
        mockUserService = Mockito.mock(UserService.class);
        mockOAuth2AuthenticationToken = Mockito.mock(OAuth2AuthenticationToken.class);
        mockAuthentication = Mockito.mock(Authentication.class);
        mockOAuth2User = Mockito.mock(OAuth2User.class);
    
        customOAuth2UserService = new CustomOAuth2UserService(mockUserService);
        
        googleUser = new User(
            "google_user_id",
            "google_user",
            "google_user@example.com",
            "USER",
            "http://example.com/google-image.png",
            "GOOGLE",
            nowInstant,
            nowInstant
        );
        githubUser = new User(
            "github_user_id",
            "github_user",
            "github_user@example.com",
            "USER",
            "http://example.com/image.png",
            "GITHUB",
            nowInstant,
            nowInstant
        );  
    }

    @Test
    void testInsertUser_ShouldReturnInsertedGoogleUser() {
        // when
        Mockito.when(mockOAuth2User.getAttribute("name"))
            .thenReturn("google_user_id");
        Mockito.when(mockOAuth2User.getAttribute("email"))
            .thenReturn("google_user@example.com");
        Mockito.when(mockOAuth2User.getAttribute("picture"))
            .thenReturn("http://example.com/google-image.png");
        Mockito.when(mockOAuth2User.getName())
            .thenReturn("user_id");
        Mockito.when(mockUserService.insertUser(Mockito.any(UserDto.class), Mockito.eq("google_user_id")))
            .thenReturn(googleUser);
        // then
        assertEquals(googleUser, customOAuth2UserService.insertUser(mockOAuth2User, "google"));
        // verify
        Mockito.verify(mockUserService).insertUser(Mockito.any(UserDto.class), Mockito.eq("google_user_id"));
    }

    @Test
    void testInsertUser_ShouldReturnInsertedGithubUser() {
        // when
        Mockito.when(mockOAuth2User.getAttribute("name"))
            .thenReturn("github_user_id");
        Mockito.when(mockOAuth2User.getAttribute("email"))
            .thenReturn("github_user@example.com");
        Mockito.when(mockOAuth2User.getAttribute("picture"))
            .thenReturn("http://example.com/github-image.png");
        Mockito.when(mockOAuth2User.getName())
            .thenReturn("user_id");
        Mockito.when(mockUserService.insertUser(Mockito.any(UserDto.class), Mockito.eq("github_user_id")))
            .thenReturn(githubUser);
        // then
        assertEquals(githubUser, customOAuth2UserService.insertUser(mockOAuth2User, "github"));
        // verify
        Mockito.verify(mockUserService).insertUser(Mockito.any(UserDto.class), Mockito.eq("github_user_id"));
    }

    @Test
    void testGetUser_ShouldReturnGoogleUserOfOAuthId() {
        // when
        Mockito.when(mockOAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
            .thenReturn("google");
        Mockito.when(mockOAuth2AuthenticationToken.getPrincipal())
            .thenReturn(mockOAuth2User);
        Mockito.when(mockOAuth2User.getName())
            .thenReturn("user_id");
        Mockito.when(mockUserService.getUserById("google_user_id"))
            .thenReturn(Optional.of(googleUser));
        // then
        assertEquals(googleUser, customOAuth2UserService.getUser(mockOAuth2AuthenticationToken));
        // verify
        Mockito.verify(mockUserService, Mockito.times(1))
            .getUserById("google_user_id");
    }
    @Test
    void testGetUser_ShouldReturnGithubUserOfOAuthId() {
        // when
        Mockito.when(mockOAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
            .thenReturn("github");
        Mockito.when(mockOAuth2AuthenticationToken.getPrincipal())
            .thenReturn(mockOAuth2User);
        Mockito.when(mockOAuth2User.getName())
            .thenReturn("user_id");
        Mockito.when(mockUserService.getUserById("github_user_id"))
            .thenReturn(Optional.of(githubUser));
        // then
        assertEquals(githubUser, customOAuth2UserService.getUser(mockOAuth2AuthenticationToken));
        // verify
        Mockito.verify(mockUserService, Mockito.times(1))
            .getUserById("github_user_id");
    }
    @Test
    void testGetUser_ShouldCallInsertUserIfUserIDNotFoundInRepository() {
        // when
        Mockito.when(mockOAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
            .thenReturn("github");
        Mockito.when(mockOAuth2AuthenticationToken.getPrincipal())
            .thenReturn(mockOAuth2User);
        Mockito.when(mockOAuth2User.getName())
            .thenReturn("user_id");
        Mockito.when(mockOAuth2User.getAttribute("login"))
            .thenReturn("google_user_id");
        Mockito.when(mockOAuth2User.getAttribute("email"))
            .thenReturn("google_user@example.com");
        Mockito.when(mockOAuth2User.getAttribute("avatar_url"))
            .thenReturn("http://example.com/google-image.png");
        Mockito.when(mockUserService.getUserById("github_user_id"))
            .thenReturn(Optional.empty());
        Mockito.when(mockUserService.insertUser(Mockito.any(UserDto.class), Mockito.eq("github_user_id")))
            .thenReturn(githubUser);
        assertEquals(githubUser, customOAuth2UserService.getUser(mockOAuth2AuthenticationToken));
        // verify
        Mockito.verify(mockUserService, Mockito.times(1)).getUserById("github_user_id");
        Mockito.verify(mockUserService).insertUser(Mockito.any(UserDto.class), Mockito.eq("github_user_id"));
    }
    @Test
    void testGetUser_ShouldReturnUserIllegalArgumentException() {
        // then
        assertThrows(UserIllegalArgumentException.class, () -> customOAuth2UserService.getUser(mockAuthentication));
    }
}
