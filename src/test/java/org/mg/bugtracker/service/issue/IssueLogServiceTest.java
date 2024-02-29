package org.mg.bugtracker.service.issue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.IssueLog;
import org.mg.bugtracker.entity.issue.dto.IssueLogDTO;
import org.mg.bugtracker.mappers.issue.IssueLogMapper;
import org.mg.bugtracker.repository.issue.IssueLogRepository;
import org.mg.bugtracker.service.user.PersonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueLogServiceTest {

    @InjectMocks
    private IssueLogService issueLogService;
    @Mock
    private IssueLogRepository issueLogRepository;
    @Mock
    private PersonService personService;
    @Mock
    private IssueLogMapper issueLogMapper;

    @Test
    void getAll_withoutParameters_returnsNotEmptyList() {
        // given
        List<IssueLog> issueLogList = new ArrayList<>();
        issueLogList.add(new IssueLog());
        issueLogList.add(new IssueLog());

        IssueLogDTO dto = new IssueLogDTO();

        // when
        when(issueLogRepository.findAll()).thenReturn(issueLogList);
        when(issueLogMapper.toIssueLogDTO(any(IssueLog.class))).thenReturn(dto);
        List<IssueLogDTO> all = issueLogService.getAll();

        // then
        assertEquals(2, all.size());
        verify(issueLogRepository, times(1)).findAll();
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        when(issueLogRepository.findAll()).thenReturn(List.of());
        List<IssueLogDTO> all = issueLogService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    void addLog() {
        // given

        int issueId = 1;
        String newStatus = "IN_PROGRESS";

        Issue issue = new Issue();
        issue.setIssueId(issueId);

        // when
        issueLogService.addIssueLog(issue, newStatus);

        // then
        verify(issueLogRepository, times(1)).save(any(IssueLog.class));
    }
}