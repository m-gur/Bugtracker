package org.mg.bugtracker.mappers.issue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.issue.IssueLog;
import org.mg.bugtracker.entity.issue.dto.IssueLogDTO;
import org.mg.bugtracker.entity.user.Person;

@Mapper
public interface IssueLogMapper {

    @Mapping(target = "person", source = "personId", qualifiedByName = "getPerson")
    @Mapping(target = "issue", source = "issueId", qualifiedByName = "getIssue")
    IssueLog toIssueLog(IssueLogDTO dto);

    @Mapping(target = "personId", source = "person", qualifiedByName = "getPersonId")
    @Mapping(target = "issueId", source = "issue", qualifiedByName = "getIssueId")
    IssueLogDTO toIssueLogDTO(IssueLog issueLog);

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
}
