package org.mg.bugtracker.entity.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedIssue {

    private String status;

    private String priority;

    private String type;

    private String name;

    private String description;

    private String code;

    private List<Integer> tagIds;

    private Integer createdId;

    private Integer assigneeId;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;

    private Integer projectId;
}
