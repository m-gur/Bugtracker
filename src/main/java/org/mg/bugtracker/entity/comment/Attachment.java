package org.mg.bugtracker.entity.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE attachments SET deleted = 1 WHERE attachment_id = ?", check = ResultCheckStyle.COUNT)
public class Attachment {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attachmentId;

    @Lob
    @NotNull
    private byte[] file;

    @NotNull
    private LocalDate dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @NotNull
    private Comment comment;

    @NotNull
    private boolean deleted;
}
