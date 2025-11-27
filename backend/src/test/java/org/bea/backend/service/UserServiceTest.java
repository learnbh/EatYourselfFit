package org.bea.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.Optional;

import org.bea.backend.enums.UserRoles;
import org.bea.backend.enums.UserTypes;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.bea.backend.model.UserUpdateDto;
import org.bea.backend.repository.UserReprository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {

    private UserReprository mockUserRepository;
    private DateService mockDateService;
    private ServiceId mockServiceId;
    private UserService userService;

    private Instant nowInstant;
    private Instant nowUpdatedInstant;

    User userGithub;
    UserDto userGithubDto;

    User updatedGithubUser;
    UserUpdateDto updatedGithubUserDto;

    User userGoogle;
    UserDto userGoogleDto;

    User userEmail;
    UserDto userEmailDto;

    User updatedEmailbUser;
    UserUpdateDto updatedEmailDto;

    @BeforeEach
    void setUp() {
        
        mockUserRepository = org.mockito.Mockito.mock(UserReprository.class);
        mockDateService = org.mockito.Mockito.mock(DateService.class);
        mockServiceId = org.mockito.Mockito.mock(ServiceId.class);

        userService = new UserService(mockUserRepository, mockDateService, mockServiceId);

        nowInstant = Instant.now();
        nowUpdatedInstant = nowInstant.plusSeconds(3600);

        userGithubDto = new UserDto(
            "github_user",
            null,
            null,
            UserTypes.GITHUB.name()
        ); 
        userGithub = new User(
            "github_user_id",
            "github_user",
            null,
            UserRoles.USER.name(),
            null,
            UserTypes.GITHUB.name(),
            nowInstant,
            nowInstant        
        );       

        updatedGithubUserDto = new UserUpdateDto(
            "github_user_upgedated",
            "test@github.com",
            "picture_url_github"
        );   
        updatedGithubUser = new User(
            "github_user_id",
            "github_user_upgedated",
            "test@github.com",
            UserRoles.USER.name(),
            "picture_url_github",
            UserTypes.GITHUB.name(),
            nowInstant,
            nowUpdatedInstant        
        );   
         
        userGoogleDto = new UserDto(
            "google_user",
            null,
            null,
            UserTypes.GOOGLE.name()
        );
        userGoogle = new User(
            "google_user_id",
            "google_user",
            null,
            UserRoles.USER.name(),
            null,
            UserTypes.GOOGLE.name(),
            nowInstant,
            nowInstant
        );
        userEmailDto = new UserDto(
            "email_user",
            "email_user@example.com",
            "image_url",
            UserTypes.EMAIL.name()
        );
        userEmail = new User(
            "email_user_id",
            "email_user",
            "email_user@example.com",
            UserRoles.USER.name(),
            "image_url",
            UserTypes.EMAIL.name(),
            nowInstant,
            nowInstant
        );
    }

    @Test
    void testGetUserById_ShouldReturnOptionalUser() {
        //when
        Mockito.when(mockUserRepository.findById("github_user_id"))
            .thenReturn(Optional.ofNullable(userGithub));
        //then
        assertEquals(Optional.of(userGithub), userService.getUserById("github_user_id"));
        // verify
        Mockito.verify(mockUserRepository, Mockito.times(1)).findById("github_user_id");
    }

    @Test
    void testGetUserById_ShouldReturnEmptyOptional() {
        //then
        assertEquals(Optional.empty(), userService.getUserById("test_user"));
        // verify
        Mockito.verify(mockUserRepository, Mockito.times(1)).findById("test_user");
    }

    @Test
    void testInsertUser_ShouldReturnInsertedOAuthUser() { 
        //when
        Mockito.when(mockDateService.getCurrentInstant())
            .thenReturn(nowInstant);
        Mockito.when(mockUserRepository.save(userGithub))
            .thenReturn(userGithub);
        //then
        User insertedUser = userService.insertUser(userGithubDto, "github_user_id");
        assertEquals("github_user_id", insertedUser.id());
        assertEquals("github_user", insertedUser.name());
        assertEquals(UserRoles.USER.name(), insertedUser.role());
        assertEquals(UserTypes.GITHUB.name(), insertedUser.type());
        assertEquals(nowInstant, insertedUser.createdAt());
        assertEquals(nowInstant, insertedUser.updatedAt());
        // verify
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(userGithub);
        Mockito.verify(mockDateService, Mockito.times(1)).getCurrentInstant();
    }
 @Test
    void testInsertUser_ShouldReturnInsertedEmail() { 
        //when
        Mockito.when(mockDateService.getCurrentInstant())
            .thenReturn(nowInstant);
        Mockito.when(mockServiceId.generateId())
            .thenReturn("user_id");
        Mockito.when(mockUserRepository.save(userEmail))
            .thenReturn(userEmail);
        //then
        assertEquals(userEmail, userService.insertUser(userEmailDto, userEmail.type()));
        // verify
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(userEmail);
        Mockito.verify(mockDateService, Mockito.times(1)).getCurrentInstant();
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUserForUserId() {
        // given
        mockUserRepository.save(userGithub);
        // when
        Mockito.when(mockUserRepository.findById("github_user_id"))
            .thenReturn(Optional.ofNullable(userGithub));
        Mockito.when(mockDateService.getCurrentInstant())
            .thenReturn(nowUpdatedInstant);
        User updatedUser = userService.updateUser("github_user_id", updatedGithubUserDto);
        // then
        assertEquals(updatedGithubUser,  updatedUser);
    }
    @Test
    void testUpdateUser_ShouldReturnIdNotFoundExceptionForUserId() {
        //then
        assertThrows(IdNotFoundException.class, ()->userService.updateUser("not_existing_id", updatedGithubUserDto));
    }
}
