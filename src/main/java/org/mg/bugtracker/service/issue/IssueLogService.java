package org.mg.bugtracker.service.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.IssueLog;
import org.mg.bugtracker.entity.issue.Status;
import org.mg.bugtracker.entity.issue.dto.IssueLogDTO;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.mappers.issue.IssueLogMapper;
import org.mg.bugtracker.repository.issue.IssueLogRepository;
import org.mg.bugtracker.service.user.PersonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueLogService {

    private final IssueLogRepository issueLogRepository;
    private final PersonService personService;
    private final IssueLogMapper issueLogMapper;

    public List<IssueLogDTO> getAll() {
        return issueLogRepository.findAll()
                .stream()
                .map(issueLogMapper::toIssueLogDTO)
                .collect(Collectors.toList());
    }

    public void addIssueLog(Issue existedIssue, String newStatus) {
        Person person = personService.findPersonForContextLogin();
        IssueLog issueLog = createIssueLog(person, existedIssue, newStatus);
        issueLogRepository.save(issueLog);
    }

    private IssueLog createIssueLog(Person person, Issue existedIssue, String newStatus) {
        IssueLog issueLog = new IssueLog();
        issueLog.setIssue(existedIssue);
        issueLog.setLogDate(LocalDate.now());
        issueLog.setPerson(person);
        issueLog.setOldStatus(existedIssue.getStatus());
        issueLog.setNewStatus(Status.valueOf(newStatus));
        return issueLog;
    }
}
