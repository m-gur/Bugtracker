package org.mg.bugtracker.service.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BatchJobService {

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final Job afterIssueSaveJob;

    public BatchJobService(JobLauncher jobLauncher, JobExplorer jobExplorer,
                           @Qualifier(AfterIssueSaveJobConfig.AFTER_ISSUE_SAVE_JOB) Job afterIssueSaveJob
                           ) {
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.afterIssueSaveJob = afterIssueSaveJob;
    }

    public void updateIssueTagIds(int issueId, List<Integer> tagIds) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var joinedTagIds = tagIds.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(UpdateIssueTagIdsJobParameters.ISSUE_ID.name(), (long) issueId)
                .addString(UpdateIssueTagIdsJobParameters.TAG_IDS.name(), joinedTagIds)
                .toJobParameters();

        jobLauncher.run(afterIssueSaveJob, jobParameters);
    }
}
