package org.mg.bugtracker.entity.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedProject {
    private String name;

    private String code;

    private String description;

}
