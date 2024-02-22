package org.mg.bugtracker.service.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AfterIssueSaveJobConfig {
    public static final String AFTER_ISSUE_SAVE_JOB = "afterIssueSaveJob";
    public static final String UPDATE_ISSUE_TAG_IDS_STEP = "updateIssueTagIds";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public AfterIssueSaveJobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean(AFTER_ISSUE_SAVE_JOB)
    public Job afterIssueSaveJob(@Qualifier(UPDATE_ISSUE_TAG_IDS_STEP) Step updateIssueTagIds) {
        return new JobBuilder(AFTER_ISSUE_SAVE_JOB, jobRepository)
                .start(updateIssueTagIds)
                .build();
    }

    @Bean(UPDATE_ISSUE_TAG_IDS_STEP)
    public Step updateIssueTagIds(UpdateIssueTagIdsTasklet updateIssueTagIdsTasklet) {
        return new StepBuilder(UPDATE_ISSUE_TAG_IDS_STEP, jobRepository)
                .tasklet(updateIssueTagIdsTasklet, platformTransactionManager)
                .build();
    }
}
