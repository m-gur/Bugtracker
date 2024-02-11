package org.mg.bugtracker.service.project;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.ProjectMapper;
import org.mg.bugtracker.repository.project.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
