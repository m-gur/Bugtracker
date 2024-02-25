package org.mg.bugtracker.service.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.Tag;
import org.mg.bugtracker.repository.issue.IssueRepository;
import org.mg.bugtracker.repository.issue.IssueTagRepository;
import org.mg.bugtracker.repository.issue.TagRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.item.ExecutionContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateIssueTagIdsTaskletTest {

    @InjectMocks
    private UpdateIssueTagIdsTasklet updateIssueTagIdsTasklet;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private IssueTagRepository issueTagRepository;

    @Test
    void execute_withoutParameters_successfulExecute() throws Exception {
        // given
        StepContribution stepContribution = createStepContribution();
        StepContext stepContext = createStepContext();
        ChunkContext chunkContext = new ChunkContext(stepContext);

        Issue issue = new Issue();
        Tag tag = new Tag();

        // when
        when(issueRepository.findById(anyInt())).thenReturn(java.util.Optional.of(issue));
        when(tagRepository.findById(anyInt())).thenReturn(java.util.Optional.of(tag));
        when(issueTagRepository.saveAll(anyList())).thenReturn(anyList());
        updateIssueTagIdsTasklet.execute(stepContribution, chunkContext);

        // then
        verify(issueRepository, times(1)).findById(anyInt());
        verify(tagRepository, times(3)).findById(anyInt());
        verify(issueTagRepository, times(1)).saveAll(anyList());
    }

    private StepContext createStepContext() {
        JobExecution jobExecution = new JobExecution(1L, createJobParameters());
        StepExecution stepExecution = new StepExecution("updateIssueTagIds", jobExecution);
        ExecutionContext stepExecutionContext = new ExecutionContext();
        stepExecution.setExecutionContext(stepExecutionContext);
        return new StepContext(stepExecution);
    }

    private StepContribution createStepContribution() {
        return new StepContribution(createStepExecution());
    }

    private StepExecution createStepExecution() {
        JobExecution jobExecution = new JobExecution(1L, createJobParameters());
        return new StepExecution("updateIssueTagIds", jobExecution);
    }

    private JobParameters createJobParameters() {
        return new JobParametersBuilder()
                .addLong(UpdateIssueTagIdsJobParameters.ISSUE_ID.name(), 1L)
                .addString(UpdateIssueTagIdsJobParameters.TAG_IDS.name(), "1,2,3")
                .toJobParameters();
    }
}