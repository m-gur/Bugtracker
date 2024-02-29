package org.mg.bugtracker.service.issue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.Status;
import org.mg.bugtracker.entity.issue.dto.IssueDTO;
import org.mg.bugtracker.entity.issue.dto.RequestedIssue;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.mappers.issue.IssueMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.mg.bugtracker.repository.issue.IssueRepository;
import org.mg.bugtracker.service.batch.BatchJobService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private IssueLogService issueLogService;
    @Mock
    private BatchJobService batchJobService;
    @Mock
    private IssueMapper issueMapper;

    @Test
    void getAll_withoutParameters_returnsNotEmptyList() {
        // given
        List<Issue> issueList = new ArrayList<>();
        issueList.add(new Issue());
        issueList.add(new Issue());

        IssueDTO dto = new IssueDTO();

        // when
        when(issueRepository.findAll()).thenReturn(issueList);
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(dto);
        List<IssueDTO> all = issueService.getAll();

        // then
        assertEquals(2, all.size());
        verify(issueRepository, times(1)).findAll();
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        when(issueRepository.findAll()).thenReturn(List.of());
        List<IssueDTO> all = issueService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    void getById_withoutParameters_issueFound() {
        // given
        Issue issue = new Issue();

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setIssueId(1);

        // when
        when(issueRepository.findById(anyInt())).thenReturn(Optional.of(issue));
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(issueDTO);
        IssueDTO foundIssue = issueService.getById(anyInt());

        // then
        assertEquals(issueDTO.getIssueId(), foundIssue.getIssueId());
    }

    @Test
    void getById_withoutParameters_ThrowsException() {
        // when & then
        assertThrows(RuntimeException.class, () -> issueService.getById(anyInt()));
    }

    @Test
    void updateIssue_withoutParameters_successfulUpdatedIssue() {
        // given
        int issueId = 1;
        String newStatus = "IN_PROGRESS";

        Issue issue = new Issue();

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setStatus(Status.IN_PROGRESS);

        // when
        when(issueRepository.findById(anyInt())).thenReturn(Optional.of(issue));
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(issueDTO);
        when(issueMapper.toIssue(any(IssueDTO.class))).thenReturn(issue);
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);
        IssueDTO updateIssue = issueService.updateIssue(issueId, newStatus);

        // then
        assertEquals(newStatus, updateIssue.getStatus().toString());
    }

    @Test
    void addIssue_withoutParameters_successfulAddedIssue() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // given
        int createdId = 1;
        Person person = new Person();
        person.setPersonId(createdId);

        RequestedIssue requestedIssue = new RequestedIssue();
        requestedIssue.setCreatedId(createdId);

        Issue issue = new Issue();
        issue.setIssueId(1);
        issue.setCreated(person);

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setCreatedId(createdId);

        // when
        when(issueMapper.fromRequest(requestedIssue)).thenReturn(issue);
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(issueDTO);
        IssueDTO addedIssue = issueService.addIssue(requestedIssue);

        // then
        assertEquals(requestedIssue.getCreatedId(), addedIssue.getCreatedId());
    }

    @Test
    void deleteIssue_withoutParameters_successDelete() {
        // when
        issueService.deleteIssue(anyInt());

        // then
        verify(attachmentRepository, times(1)).deleteAttachmentsByIssueId(anyInt());
        verify(commentRepository, times(1)).deleteByIssueId(anyInt());
        verify(issueRepository, times(1)).deleteById(anyInt());
    }
}