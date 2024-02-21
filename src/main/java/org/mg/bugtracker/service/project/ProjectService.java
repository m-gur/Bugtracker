package org.mg.bugtracker.service.project;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.project.Project;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.ProjectMapper;
import org.mg.bugtracker.entity.project.dto.RequestedProject;
import org.mg.bugtracker.repository.project.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDTO> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toProjectDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getById(int projectId) {
        return projectRepository.findById(projectId)
                .map(projectMapper::toProjectDTO)
                .orElseThrow(() -> new RuntimeException("Cannot find project with requested id!"));
    }

    public ProjectDTO addProject(RequestedProject requestedProject) {
        Optional<Project> existedProject = projectRepository.findByName(requestedProject.getName());
        if (existedProject.isEmpty()) {
            ProjectDTO projectDTO = projectMapper.fromRequest(requestedProject);
            projectDTO.setEnabled(false);
            projectDTO.setDateCreated(LocalDate.now());
            Project newProject = projectMapper.toProject(projectDTO);
            return projectMapper.toProjectDTO(projectRepository.save(newProject));
        }
        else throw new RuntimeException("Cannot create project with provided name!");
    }

    public void disableProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}
