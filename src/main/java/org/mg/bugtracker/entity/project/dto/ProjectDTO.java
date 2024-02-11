package org.mg.bugtracker.entity.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {

    private Integer projectId;

    private String name;

    private List<Integer> issueIds;

    private boolean enabled;

    private LocalDate dateCreated;

    private String code;

    private String description;

}
