package org.mg.bugtracker.entity.comment.dto;

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
public class CommentDTO {
    private Integer commentId;

    private String content;

    private Integer personId;

    private Integer issueId;

    private List<Integer> attachmentIds;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;

    private boolean deleted;

}
