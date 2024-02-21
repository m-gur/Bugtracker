package org.mg.bugtracker.service.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.project.Project;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.ProjectMapper;
import org.mg.bugtracker.entity.project.dto.RequestedProject;
import org.mg.bugtracker.repository.project.ProjectRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;

    @Test
    void getAll_withoutParameters_returnsNotEmptyList() {
        // given
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project());
        projectList.add(new Project());

        ProjectDTO dto = new ProjectDTO();

        // when
        when(projectRepository.findAll()).thenReturn(projectList);
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(dto);
        List<ProjectDTO> all = projectService.getAll();

        // then
        assertEquals(2, all.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        when(projectRepository.findAll()).thenReturn(List.of());
        List<ProjectDTO> all = projectService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    void getById_withoutParameters_ProjectFound() {
        // given
        Project project = new Project();

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(1);

        // when
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(projectDTO);
        ProjectDTO projectById = projectService.getById(anyInt());

        // then
        assertEquals(projectDTO.getProjectId(), projectById.getProjectId());
    }

    @Test
    void getById_withoutParameters_ThrowsException() {
        // when & then
        assertThrows(RuntimeException.class, () -> projectService.getById(anyInt()));
    }

    @Test
    void addProject_withoutParameters_successProjectAdded() {
        // given
        String name = "project";
        RequestedProject requestedProject = new RequestedProject();
        requestedProject.setName(name);

        Project project = new Project();
        project.setName(name);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);

        // when
        when(projectRepository.findByName(requestedProject.getName())).thenReturn(Optional.empty());
        when(projectMapper.fromRequest(requestedProject)).thenReturn(projectDTO);
        when(projectMapper.toProject(any(ProjectDTO.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toProjectDTO(project)).thenReturn(projectDTO);
        ProjectDTO addedProject = projectService.addProject(requestedProject);

        // then
        assertEquals(requestedProject.getName(), addedProject.getName());
    }

    @Test
    void addProject_withoutParameters_throwsException() {
        // given
        String name = "project";
        RequestedProject requestedProject = new RequestedProject();
        requestedProject.setName(name);

        // when
        when(projectRepository.findByName(requestedProject.getName())).thenReturn(Optional.of(new Project()));

        // then
        assertThrows(RuntimeException.class, () -> projectService.addProject(requestedProject));
    }

    @Test
    void disableProject_withoutParameters_successDisable() {
        // when
        projectService.disableProject(anyInt());

        // then
        verify(projectRepository, times(1)).deleteById(anyInt());
    }
}