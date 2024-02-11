package org.mg.bugtracker.service.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.dto.IssueLogDTO;
import org.mg.bugtracker.entity.issue.dto.IssueLogMapper;
import org.mg.bugtracker.repository.issue.IssueLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueLogService {

    private final IssueLogRepository issueLogRepository;
    private final IssueLogMapper issueLogMapper;

    public List<IssueLogDTO> getAll() {
        return issueLogRepository.findAll()
                .stream()
                .map(issueLogMapper::toIssueLogDTO)
                .collect(Collectors.toList());
    }
}
