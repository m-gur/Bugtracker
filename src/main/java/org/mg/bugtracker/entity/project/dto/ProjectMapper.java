package org.mg.bugtracker.entity.project.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.project.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProjectMapper {
    @Mapping(target = "issues", source = "issueIds", qualifiedByName = "getIssues")
    Project toProject(ProjectDTO dto);

    @Mapping(target = "issueIds", source = "issues", qualifiedByName = "getIssueIds")
    ProjectDTO toProjectDTO(Project project);

    @Named("getIssues")
    default List<Issue> getIssues(List<Integer> issueIds) {
        if (!issueIds.isEmpty()) {
            List<Issue> issues = new ArrayList<>();
            for (Integer issueId : issueIds) {
                Issue issue = new Issue();
                issue.setIssueId(issueId);
                issues.add(issue);
            }
            return issues;
        }
        return Collections.emptyList();
    }

    @Named("getIssueIds")
    default List<Integer> getIssueIds(List<Issue> issues) {
        return (issues != null) ? issues
                .stream()
                .map(Issue::getIssueId)
                .collect(Collectors.toList()) : null;
    }
}
