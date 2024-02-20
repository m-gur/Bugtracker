package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.RequestedTag;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.service.issue.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController extends BugTrackerAbstractController {

    private final TagService tagService;

    @GetMapping(value = "/tags/all")
    public List<TagDTO> getAll() {
        return tagService.getAll();
    }

    @GetMapping(value = "/tags/{tagId}")
    public TagDTO getTagById(@PathVariable int tagId) {
        return tagService.getById(tagId);
    }

    @PostMapping(value = "/tags/add")
    public TagDTO addTag(@RequestBody RequestedTag requestedTag) {
        return tagService.addTag(requestedTag);
    }

    @DeleteMapping(value = "/tags/{tagId}")
    public void deleteTag(@PathVariable int tagId) {
        tagService.deleteTag(tagId);
    }
}
