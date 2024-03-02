package org.mg.bugtracker.service.project;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.project.Project;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.RequestedProject;
import org.mg.bugtracker.mappers.project.ProjectMapper;
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
            Project newProject = createProject(requestedProject);
            return projectMapper.toProjectDTO(projectRepository.save(newProject));
        }
        else throw new EntityExistsException("Cannot create project with provided name!");
    }

    @Transactional
    public void enableProject(int projectId) {
        projectRepository.enableProjectById(projectId);
    }

    public void disableProject(int projectId) {
        projectRepository.deleteById(projectId);
    }

    private Project createProject(RequestedProject requestedProject) {
        Project newProject = projectMapper.fromRequest(requestedProject);
        newProject.setDateCreated(LocalDate.now());
        newProject.setEnabled(true);
        return newProject;
    }
}
