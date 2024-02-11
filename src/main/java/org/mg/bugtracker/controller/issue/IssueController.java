package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.service.issue.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueController extends BugTrackerAbstractController {

    private final IssueService issueService;

    @GetMapping(value = "/issues/all")
    public List<IssueDTO> getAll() {
        return issueService.getAll();
    }
}
