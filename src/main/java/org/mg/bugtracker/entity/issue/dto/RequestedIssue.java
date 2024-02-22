package org.mg.bugtracker.entity.issue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate;

    private Integer projectId;
}
