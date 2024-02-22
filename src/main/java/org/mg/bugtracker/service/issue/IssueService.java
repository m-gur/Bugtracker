package org.mg.bugtracker.service.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.Status;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.entity.issue.dto.IssueMapper;
import org.mg.bugtracker.entity.issue.dto.RequestedIssue;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.mg.bugtracker.repository.issue.IssueRepository;
import org.mg.bugtracker.service.batch.BatchJobService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final AttachmentRepository attachmentRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final IssueLogService issueLogService;
    private final BatchJobService batchJobService;
    private final IssueMapper issueMapper;

    public List<IssueDTO> getAll() {
        return issueRepository.findAll()
                .stream()
                .map(issueMapper::toIssueDTO)
                .collect(Collectors.toList());
    }

    public IssueDTO getById(int issueId) {
        return issueRepository.findById(issueId)
                .map(issueMapper::toIssueDTO)
                .orElseThrow(() -> new RuntimeException("Cannot find issue with requested id!"));
    }

    public IssueDTO updateIssue(int issueId, String newStatus) {
        Issue existedIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Cannot find issue with requested id!"));
        issueLogService.addLog(existedIssue, newStatus);
        IssueDTO issueDTO = issueMapper.toIssueDTO(existedIssue);
        issueDTO.setStatus(Status.valueOf(newStatus));
        Issue issueWithUpdatedStatus = issueMapper.toIssue(issueDTO);
        return issueMapper.toIssueDTO(issueRepository.save(issueWithUpdatedStatus));
    }

    public IssueDTO addIssue(RequestedIssue requestedIssue) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        IssueDTO fromRequest = issueMapper.fromRequest(requestedIssue);
        fromRequest.setDeleted(false);
        fromRequest.setCommentIds(List.of());
        fromRequest.setIssueTagIds(List.of());

        Issue newIssue = issueMapper.toIssue(fromRequest);
        Issue addedIssue = issueRepository.save(newIssue);

        batchJobService.updateIssueTagIds(addedIssue.getIssueId(), requestedIssue.getTagIds());

        return issueMapper.toIssueDTO(addedIssue);
    }

    public void deleteIssue(int issueId) {
        attachmentRepository.deleteAttachmentsByIssueId(issueId);
        commentRepository.deleteByIssueId(issueId);
        issueRepository.deleteById(issueId);
    }
}
