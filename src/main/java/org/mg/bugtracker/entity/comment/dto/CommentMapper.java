package org.mg.bugtracker.entity.comment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.comment.Attachment;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.user.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CommentMapper {

    @Mapping(target = "person", source = "personId", qualifiedByName = "getPerson")
    @Mapping(target = "issue", source = "issueId", qualifiedByName = "getIssue")
    @Mapping(target = "attachments", source = "attachmentIds", qualifiedByName = "getAttachments")
    Comment toComment(CommentDTO dto);

    @Mapping(target = "personId", source = "person", qualifiedByName = "getPersonId")
    @Mapping(target = "issueId", source = "issue", qualifiedByName = "getIssueId")
    @Mapping(target = "attachmentIds", source = "attachments", qualifiedByName = "getAttachmentIds")
    CommentDTO toCommentDTO(Comment comment);

    @Mapping(target = "person", ignore = true)
    @Mapping(target = "issue", source = "issueId", qualifiedByName = "getIssue")
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    Comment addComment(RequestedComment requestedComment);

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

    @Named("getIssue")
    default Issue getIssue(Integer issueId) {
        if (issueId != null) {
            Issue issue = new Issue();
            issue.setIssueId(issueId);
            return issue;
        }
        return null;
    }

    @Named("getIssueId")
    default Integer getIssueId(Issue issue) {
        return (issue != null) ? issue.getIssueId() : null;
    }

    @Named("getAttachments")
    default List<Attachment> getAttachments(List<Integer> attachmentIds) {
        if (!attachmentIds.isEmpty()) {
            List<Attachment> attachments = new ArrayList<>();
            for (Integer attachmentId : attachmentIds) {
                Attachment attachment = new Attachment();
                attachment.setAttachmentId(attachmentId);
                attachments.add(attachment);
            }
            return attachments;
        }
        return Collections.emptyList();
    }

    @Named("getAttachmentIds")
    default List<Integer> getAttachmentIds(List<Attachment> attachments) {
        return (attachments != null) ? attachments
                .stream()
                .map(Attachment::getAttachmentId)
                .collect(Collectors.toList()) : null;
    }
}

