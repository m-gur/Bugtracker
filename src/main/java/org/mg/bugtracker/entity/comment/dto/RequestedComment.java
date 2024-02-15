package org.mg.bugtracker.entity.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestedComment {

    private String content;

    private Integer personId;

    private Integer issueId;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;
}
