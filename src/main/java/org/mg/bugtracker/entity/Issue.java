package org.mg.bugtracker.entity;

import jakarta.persistence.*;
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

    private Status status;

    private Priority priority;

    private Type type;

    private String name;

    private String description;

    private String code;

    @OneToMany(mappedBy = "issue")
    private List<IssueTag> issueTags;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Person created;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Person assignee;

    private LocalDate dateCreated;

    private LocalDate lastUpdate;

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;

    private boolean deleted;
}
