package org.bea.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpaFallbackControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void recipeShouldForwardToIndexHtml() throws Exception {
        mockMvc.perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void loginSuccessShouldForwardToIndexHtml() throws Exception {
        mockMvc.perform(get("/login/success"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/index.html"));
    }
}