package org.mg.bugtracker.service.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BatchJobServiceTest {

    @InjectMocks
    private BatchJobService batchJobService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private JobExplorer jobExplorer;

    @Mock
    @Qualifier(AfterIssueSaveJobConfig.AFTER_ISSUE_SAVE_JOB)
    private Job afterIssueSaveJob;

    @Test
    void updateIssueTagIds() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // given
        int issueId = 1;
        List<Integer> tagIds = Arrays.asList(1, 2);

        // when
        batchJobService.updateIssueTagIds(issueId, tagIds);

        // then
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }
}