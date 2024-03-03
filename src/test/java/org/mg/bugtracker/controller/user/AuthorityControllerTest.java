package org.mg.bugtracker.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.RequestedAuthority;
import org.mg.bugtracker.service.user.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthorityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorityService authorityService;

    @Test
    void getAll_withoutParameters_returns200ok() throws Exception {
        // given
        List<AuthorityDTO> authorityDTOList = new ArrayList<>();
        authorityDTOList.add(new AuthorityDTO());
        authorityDTOList.add(new AuthorityDTO());

        // when
        when(authorityService.getAll()).thenReturn(authorityDTOList);

        // then
        mockMvc.perform(get("/bugtracker/authorities/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(authorityDTOList.size()));
    }

    @Test
    void getById_withoutParameters_returns200ok() throws Exception {
        // given
        int authorityId = 1;
        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setAuthorityId(authorityId);

        // when
        when(authorityService.getAuthorityById(authorityId)).thenReturn(authorityDTO);

        // then
        mockMvc.perform(get("/bugtracker/authorities/{authorityId}", authorityId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorityId").value(authorityId));
    }

    @Test
    void addAuthority_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedAuthority requestedAuthority = new RequestedAuthority();
        requestedAuthority.setName("supp");

        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setName("supp");

        // when
        when(authorityService.addAuthority(requestedAuthority)).thenReturn(authorityDTO);

        // then
        mockMvc.perform(post("/bugtracker/authorities/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedAuthority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorityDTO.getName()));
    }

    @Test
    void deleteAuthority_withoutParameters_returns200ok() throws Exception {
        // given
        int authorityId = 1;

        // when & then
        mockMvc.perform(delete("/bugtracker/authorities/{authorityId}", authorityId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void accessProtectedResourceWithDifferentRole() throws Exception {
        // when & then
        mockMvc.perform(get("/bugtracker/authorities/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").password("password").authorities(AuthorityUtils.createAuthorityList("USER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAuthority_withoutParameters_returns200ok() throws Exception {
        // given
        String authority = "ADMIN";

        // when
        when(authorityService.getAuthority()).thenReturn(authority);

        // then
        mockMvc.perform(get("/bugtracker/default-authority")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().string(authority));
    }
}