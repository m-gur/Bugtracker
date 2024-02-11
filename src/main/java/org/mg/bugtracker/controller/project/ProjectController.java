package org.mg.bugtracker.controller.project;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.service.project.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController extends BugTrackerAbstractController {

    private final ProjectService projectService;

    @GetMapping(value = "projects/all")
    public List<ProjectDTO> getAll() {
        return projectService.getAll();
    }
}
