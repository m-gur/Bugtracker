package org.mg.bugtracker.controller.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.entity.issue.dto.RequestedIssue;
import org.mg.bugtracker.service.issue.IssueService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IssueService issueService;

    @Test
    void getAll_withoutParameters_returns200ok() throws Exception {
        // given
        List<IssueDTO> issueDTOList = new ArrayList<>();
        issueDTOList.add(new IssueDTO());
        issueDTOList.add(new IssueDTO());

        // when
        when(issueService.getAll()).thenReturn(issueDTOList);

        // then
        mockMvc.perform(get("/bugtracker/issues/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(issueDTOList.size()));
    }

    @Test
    void getById_withoutParameters_returns200ok() throws Exception {
        // given
        int issueId = 1;
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setIssueId(issueId);

        // when
        when(issueService.getById(issueId)).thenReturn(issueDTO);

        // then
        mockMvc.perform(get("/bugtracker/issues/{issueId}", issueId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issueId").value(issueDTO.getIssueId()));
    }

    @Test
    void updateIssue_withoutParameters_returns200ok() throws Exception {
        // given
        int issueId = 1;
        String newStatus = "IN_PROGRESS";
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setIssueId(issueId);

        // when
        when(issueService.updateIssue(issueId, newStatus)).thenReturn(issueDTO);

        // then
        mockMvc.perform(put("/bugtracker/issues/{issueId}", issueId)
                        .param("newStatus", newStatus)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issueId").value(issueDTO.getIssueId()));
    }

    @Test
    void addIssue_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedIssue requestedIssue = new RequestedIssue();
        requestedIssue.setCreatedId(1);

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setCreatedId(1);

        // when
        when(issueService.addIssue(requestedIssue)).thenReturn(issueDTO);

        // then
        mockMvc.perform(post("/bugtracker/issues/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedIssue))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdId").value(issueDTO.getCreatedId()));
    }

    @Test
    void deleteIssue_withoutParameters_returns200ok() throws Exception {
        // given
        int issueId = 1;
        // when & then
        mockMvc.perform(delete("/bugtracker/issues/{issueId}", issueId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}