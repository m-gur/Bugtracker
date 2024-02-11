package org.mg.bugtracker.entity.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mg.bugtracker.entity.issue.Issue;
import org.mg.bugtracker.entity.user.Person;

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
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @NotNull
    private LocalDate dateCreated;

    @NotNull
    private LocalDate lastUpdate;

    private String content;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @NotNull
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    @NotNull
    private Issue issue;

    @NotNull
    private boolean deleted;
}
