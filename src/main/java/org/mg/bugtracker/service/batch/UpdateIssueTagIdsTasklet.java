package org.mg.bugtracker.service.batch;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.IssueTag;
import org.mg.bugtracker.entity.issue.IssueTagId;
import org.mg.bugtracker.entity.issue.Tag;
import org.mg.bugtracker.repository.issue.IssueRepository;
import org.mg.bugtracker.repository.issue.IssueTagRepository;
import org.mg.bugtracker.repository.issue.TagRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UpdateIssueTagIdsTasklet implements Tasklet {
    private final TagRepository tagRepository;
    private final IssueRepository issueRepository;
    private final IssueTagRepository issueTagRepository;

    @Override
    @Transactional
    public RepeatStatus execute(@NonNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws Exception {

        int issueId = Math.toIntExact((Long) chunkContext.getStepContext().getJobParameters()
                .get(UpdateIssueTagIdsJobParameters.ISSUE_ID.name()));
        String joinedTagIds = (String) chunkContext.getStepContext().getJobParameters()
                .get(UpdateIssueTagIdsJobParameters.TAG_IDS.name());

        List<Integer> tagIds = Arrays.stream(joinedTagIds.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Cannot find issue with requested id!"));
        createIssueTags(issue, tagIds);

        return RepeatStatus.FINISHED;
    }

    private void createIssueTags(Issue issue, List<Integer> tagIds) {
        List<IssueTag> issueTags = new ArrayList<>();

        for (Integer tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("Cannot find tag with requested id!"));

            IssueTagId issueTagId = new IssueTagId(issue.getIssueId(), tag.getTagId());


            IssueTag issueTag = new IssueTag();
            issueTag.setId(issueTagId);
            issueTag.setIssue(issue);
            issueTag.setTag(tag);
            issueTags.add(issueTag);
        }

        issueTagRepository.saveAll(issueTags);
    }
}
