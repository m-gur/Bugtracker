package org.mg.bugtracker.entity.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mg.bugtracker.entity.issue.Status;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueLogDTO {

    private Integer issueLogId;

    private Integer personId;

    private Integer issueId;

    private Status oldStatus;

    private Status newStatus;

    private LocalDate logDate;
}
