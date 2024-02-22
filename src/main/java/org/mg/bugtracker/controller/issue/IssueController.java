package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.service.issue.IssueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueController extends BugTrackerAbstractController {

    private final IssueService issueService;

    @GetMapping(value = "/issues/all")
    public List<IssueDTO> getAll() {
        return issueService.getAll();
    }

    @GetMapping(value = "/issues/{issueId}")
    public IssueDTO getById(@PathVariable int issueId) {
        return issueService.getById(issueId);
    }

    @PutMapping(value = "/issues/{issueId}")
    public IssueDTO updateIssue(@PathVariable int issueId, @RequestParam String newStatus) {
        return issueService.updateIssue(issueId, newStatus);
    }

    @DeleteMapping(value = "/issues/{issueId}")
    public void deleteIssue(@PathVariable int issueId) {
        issueService.deleteIssue(issueId);
    }
}
