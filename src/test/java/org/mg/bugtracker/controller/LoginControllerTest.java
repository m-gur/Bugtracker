package org.mg.bugtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.user.dto.RequestedLogin;
import org.mg.bugtracker.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    LoginService loginService;

    @Test
    void addLogin_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedLogin requestedLogin = new RequestedLogin("login", "password", "email@email.email");

        // when & then
        mockMvc.perform(post("/add-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedLogin)))
                .andExpect(status().isOk());
    }

    @Test
    void addLogin_withoutParameters_returns400BadRequest() throws Exception {
        // given, when & then
        mockMvc.perform(post("/add-login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}