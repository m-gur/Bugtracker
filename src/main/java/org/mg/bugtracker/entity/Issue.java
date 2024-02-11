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
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String name;

    private String description;

    private String code;

    @OneToMany(mappedBy = "issue")
    private List<IssueTag> issueTags;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NotNull
    private Person created;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @NotNull
    private Person assignee;

    @NotNull
    private LocalDate dateCreated;

    @NotNull
    private LocalDate lastUpdate;

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;

    @NotNull
    private boolean deleted;
}
