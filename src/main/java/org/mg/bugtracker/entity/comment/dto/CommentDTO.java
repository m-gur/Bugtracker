package org.mg.bugtracker.entity.comment.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Integer commentId;

    private String content;

    private Integer personId;

    private Integer issueId;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;

    private boolean deleted;

}
