package org.mg.bugtracker.entity.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mg.bugtracker.entity.issue.IssueTagId;
import org.mg.bugtracker.entity.issue.Priority;
import org.mg.bugtracker.entity.issue.Status;
import org.mg.bugtracker.entity.issue.Type;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueDTO {

    private Integer issueId;

    private Status status;

    private Priority priority;

    private Type type;

    private String name;

    private String description;

    private String code;

    private List<IssueTagId> issueTagIds;

    private Integer createdId;

    private Integer assigneeId;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;

    private List<Integer> commentIds;

    private Integer projectId;

    private boolean deleted;
}
