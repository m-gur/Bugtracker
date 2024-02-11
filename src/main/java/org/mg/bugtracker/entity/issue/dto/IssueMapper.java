package org.mg.bugtracker.entity.issue.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.IssueTag;
import org.mg.bugtracker.entity.issue.IssueTagId;
import org.mg.bugtracker.entity.project.Project;
import org.mg.bugtracker.entity.user.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IssueMapper {

    @Mapping(target = "project", source = "projectId", qualifiedByName = "getProject")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "getPerson")
    @Mapping(target = "created", source = "createdId", qualifiedByName = "getPerson")
    @Mapping(target = "comments", source = "commentIds", qualifiedByName = "getComments")
    @Mapping(target = "issueTags", source = "issueTagIds", qualifiedByName = "getIssueTags")
    Issue toIssue(IssueDTO dto);

    @Mapping(target = "projectId", source = "project", qualifiedByName = "getProjectId")
    @Mapping(target = "assigneeId", source = "assignee", qualifiedByName = "getPersonId")
    @Mapping(target = "createdId", source = "created", qualifiedByName = "getPersonId")
    @Mapping(target = "commentIds", source = "comments", qualifiedByName = "getCommentIds")
    @Mapping(target = "issueTagIds", source = "issueTags", qualifiedByName = "getIssueTagIds")
    IssueDTO toIssueDTO(Issue issue);

    @Named("getPerson")
    default Person getPerson(Integer personId) {
        if (personId != null) {
            Person person = new Person();
            person.setPersonId(personId);
            return person;
        }
        return null;
    }

    @Named("getPersonId")
    default Integer getPersonId(Person person) {
        return (person != null) ? person.getPersonId() : null;
    }

    @Named("getProject")
    default Project getProject(Integer projectId) {
        if (projectId != null) {
            Project project = new Project();
            project.setProjectId(projectId);
            return project;
        }
        return null;
    }

    @Named("getProjectId")
    default Integer getProjectId(Project project) {
        return (project != null) ? project.getProjectId() : null;
    }

    @Named("getComments")
    default List<Comment> getComments(List<Integer> commentIds) {
        if (!commentIds.isEmpty()) {
            List<Comment> comments = new ArrayList<>();
            for (Integer commentId : commentIds) {
                Comment comment = new Comment();
                comment.setCommentId(commentId);
                comments.add(comment);
            }
            return comments;
        }
        return Collections.emptyList();
    }

    @Named("getCommentIds")
    default List<Integer> getCommentIds(List<Comment> comments) {
        return (comments != null) ? comments
                .stream()
                .map(Comment::getCommentId)
                .collect(Collectors.toList()) : null;
    }

    @Named("getIssueTags")
    default List<IssueTag> getIssueTags(List<IssueTagId> issueTagIds) {
        if (!issueTagIds.isEmpty()) {
            List<IssueTag> issueTags = new ArrayList<>();
            for (IssueTagId issueTagId : issueTagIds) {
                IssueTag issueTag = new IssueTag();
                issueTag.setId(issueTagId);
                issueTags.add(issueTag);
            }
            return issueTags;
        }
        return Collections.emptyList();
    }

    @Named("getIssueTagIds")
    default List<IssueTagId> getIssueTagIds(List<IssueTag> issueTags) {
        return (issueTags != null) ? issueTags
                .stream()
                .map(IssueTag::getId)
                .collect(Collectors.toList()) : null;
    }
}
