package org.mg.bugtracker.controller.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.issue.dto.IssueLogDTO;
import org.mg.bugtracker.service.issue.IssueLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueLogController extends BugTrackerAbstractController {

    private final IssueLogService issueLogService;

    @GetMapping(value = "issue-logs/all")
    public List<IssueLogDTO> getAll() {
        return issueLogService.getAll();
    }
}
