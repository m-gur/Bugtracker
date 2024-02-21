package org.mg.bugtracker.controller.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.RequestedProject;
import org.mg.bugtracker.service.project.ProjectService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;
    @Test
    void getAll_withoutParameters_returns200ok() throws Exception {
        // given
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        projectDTOList.add(new ProjectDTO());
        projectDTOList.add(new ProjectDTO());

        // when
        when(projectService.getAll()).thenReturn(projectDTOList);

        // then
        mockMvc.perform(get("/bugtracker/projects/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(projectDTOList.size()));
    }

    @Test
    void getTagById_withoutParameters_returns200ok() throws Exception {
        // given
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectId);

        // when
        when(projectService.getById(projectId)).thenReturn(projectDTO);

        // then
        mockMvc.perform(get("/bugtracker/projects/{projectId}", projectId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(projectId));
    }

    @Test
    void addProject_withoutParameters_returns200ok() throws Exception {
        // given
        String name = "project";
        RequestedProject requestedProject = new RequestedProject();
        requestedProject.setName(name);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);

        // when
        when(projectService.addProject(requestedProject)).thenReturn(projectDTO);

        // then
        mockMvc.perform(post("/bugtracker/projects/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(projectDTO.getName()));
    }

    @Test
    void disableProject_withoutParameters_returns200ok() throws Exception {
        // given
        int projectId = 1;

        // when & then
        mockMvc.perform(delete("/bugtracker/projects/{projectId}", projectId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}