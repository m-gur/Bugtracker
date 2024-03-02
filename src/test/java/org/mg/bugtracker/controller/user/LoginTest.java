package org.mg.bugtracker.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSuccessfulLogin() throws Exception {
        // given, when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admin")
                        .param("password", "admin"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin-panel.html"));
    }

    @Test
    void testFailureLogin() throws Exception {
        // given, when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admin")
                        .param("password", "none"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testSuccessfulLogout() throws Exception {
        // given, when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
}
