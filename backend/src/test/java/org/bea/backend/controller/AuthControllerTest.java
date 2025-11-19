package org.bea.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

import org.bea.backend.enums.UserRoles;
import org.bea.backend.enums.UserTypes;
import org.bea.backend.model.User;
import org.bea.backend.repository.UserReprository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserReprository mockUserReprository;

    ObjectMapper objectMapper = new ObjectMapper();
    User user = new User(
        "test_user",
        "github_user",
        null,
        UserRoles.USER.name(),
        null,
        UserTypes.GITHUB.name(),
        java.time.Instant.now(),
        java.time.Instant.now()
    );
    User userGoogle = new User(
        "google_user",
        "google_user",
        null,
        UserRoles.USER.name(),
        null,
        UserTypes.GOOGLE.name(),
        java.time.Instant.now(),
        java.time.Instant.now()
    );
    @Test
    void testGetLoginInfo_shouldReturnUserInfos() throws Exception {
        mockUserReprository.save(user);
        objectMapper.registerModule(new JavaTimeModule()); 
        objectMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        mockMvc.perform(MockMvcRequestBuilders.get("/eyf/login/success")
                .with(oidcLogin().userInfoToken(token->token.claim("login","test_user"))))        
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(mockUserReprository.findById("test_user").get())));
    }
}
