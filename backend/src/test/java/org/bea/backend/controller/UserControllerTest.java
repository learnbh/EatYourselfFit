package org.bea.backend.controller;

import java.time.Instant;

import org.bea.backend.enums.UserRoles;
import org.bea.backend.enums.UserTypes;
import org.bea.backend.exception.UserNotFoundException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.bea.backend.model.UserUpdateDto;
import org.bea.backend.repository.UserReprository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class UserControllerTest {    
        
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserReprository mockUserRepository;

    ObjectMapper mapper = new ObjectMapper();

    Instant nowInstant = Instant.now();
    String nowString = nowInstant.toString();
    String nowUpdatedString = nowInstant.plusSeconds(3600).toString();

    UserDto userEmailDto = new UserDto(
        "email_user",
        "email_user@example.com",
        "image_url",
        UserTypes.EMAIL.name()
    );
    User userEmail = new User(
        "email_user_id",
        "email_user",
        "email_user@example.com",
        UserRoles.USER.name(),
        "image_url",
        UserTypes.EMAIL.name(),
        nowInstant,
        nowInstant.plusSeconds(3600)
    );
    String userEmailString = """
                {
                  "id": "email_user_id",
                  "name": "email_user",
                  "email": "email_user@example.com",
                  "role": "USER",
                  "imageUrl": "image_url",
                  "type": "EMAIL",
                  "createdAt": "%s",
                  "updatedAt": "%s"
                }""".formatted(nowString, nowString);

    UserUpdateDto userEmailUpdateDto = new UserUpdateDto(
        "email_user_update",
        "email_user@update-example.com",
        "image_url_update"
    );

    @Test
    void testGetUserById_ShouldReturnUser() throws Exception {
        // given
        mockUserRepository.save(userEmail);
        mapper.registerModule(new JavaTimeModule()); 
        mapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'"));
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/users/email_user_id"))
        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(mockUserRepository.findById("email_user_id").get())));
    }

    @Test
    void testGetUserByIdShouldReturnUserNotFoundExceptionForUserId() throws UserNotFoundException, Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/users/test_user"))
        // then
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Error: User not found"));
    }

    @Test
    void testInsertUser_ShouldReturnInsertedUser() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/eyf/users/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userEmailDto)))
        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("email_user"))
            .andExpect(MockMvcResultMatchers.jsonPath("email").value("email_user@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("role").value("USER"))
            .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("image_url"))
            .andExpect(MockMvcResultMatchers.jsonPath("type").value("EMAIL"))
            .andExpect(MockMvcResultMatchers.jsonPath("createdAt").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("updatedAt").isNotEmpty()); 

    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUserForUserId() throws Exception {
        // given
        mockUserRepository.save(userEmail);
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/eyf/users/update/" + userEmail.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userEmailUpdateDto)))
        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value("email_user_id"))
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("email_user_update"))
            .andExpect(MockMvcResultMatchers.jsonPath("email").value("email_user@update-example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("role").value("USER"))
            .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("image_url_update"))
            .andExpect(MockMvcResultMatchers.jsonPath("type").value("EMAIL"))
            .andExpect(MockMvcResultMatchers.jsonPath("createdAt").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("updatedAt").isNotEmpty());             
    }
    @Test
    void testUpdateUser_ShouldReturnUserNotFoundExceptionForUserId() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/eyf/users/update/" + userEmail.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userEmailUpdateDto)))
        // then
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("error").value("Error: Id email_user_id of User not found"));             
    }

}