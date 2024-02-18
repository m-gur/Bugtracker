package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.service.issue.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController extends BugTrackerAbstractController {

    private final TagService tagService;

    @GetMapping(value = "/tags/all")
    public List<TagDTO> getAll() {
        return tagService.getAll();
    }
}
