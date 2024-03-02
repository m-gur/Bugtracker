package org.mg.bugtracker.controller.project;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.project.dto.ProjectDTO;
import org.mg.bugtracker.entity.project.dto.RequestedProject;
import org.mg.bugtracker.service.project.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController extends BugTrackerAbstractController {

    private final ProjectService projectService;

    @GetMapping(value = "/projects/all")
    public List<ProjectDTO> getAll() {
        return projectService.getAll();
    }

    @GetMapping(value = "/projects/{projectId}")
    public ProjectDTO getById(@PathVariable int projectId) {
        return projectService.getById(projectId);
    }

    @PostMapping(value = "/projects/add")
    public ProjectDTO addProject(@RequestBody RequestedProject requestedProject) {
        return projectService.addProject(requestedProject);
    }

    @PutMapping(value = "/projects/{projectId}")
    public void enableProject(@PathVariable int projectId) {
        projectService.enableProject(projectId);
    }

    @DeleteMapping(value = "/projects/{projectId}")
    public void disableProject(@PathVariable int projectId) {
        projectService.disableProject(projectId);
    }
}
