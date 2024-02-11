package org.mg.bugtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @NotNull
    private LocalDate dateCreated;

    @NotNull
    private LocalDate lastUpdate;

    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull
    private Person person;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @NotNull
    private Issue issue;

    @NotNull
    private boolean deleted;
}
