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
public class AttachmentDTO {

    private Integer attachmentId;

    private byte[] data;

    private String name;

    private String type;

    private LocalDate dateAdded;

    private Integer commentId;

    private boolean deleted;

}
