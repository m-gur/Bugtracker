package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.entity.issue.dto.RequestedIssue;
import org.mg.bugtracker.service.issue.IssueService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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

    @PostMapping(value = "/issues/add")
    public IssueDTO addIssue(@RequestBody RequestedIssue requestedIssue) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        return issueService.addIssue(requestedIssue);
    }

    @DeleteMapping(value = "/issues/{issueId}")
    public void deleteIssue(@PathVariable int issueId) {
        issueService.deleteIssue(issueId);
    }
}
