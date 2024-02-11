package org.mg.bugtracker.service.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.entity.issue.dto.IssueMapper;
import org.mg.bugtracker.repository.issue.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public List<IssueDTO> getAll() {
        return issueRepository.findAll()
                .stream()
                .map(issueMapper::toIssueDTO)
                .collect(Collectors.toList());
    }
}
