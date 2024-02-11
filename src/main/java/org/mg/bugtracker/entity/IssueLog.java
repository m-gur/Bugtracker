package org.mg.bugtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "issue_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueLogId;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull
    private Person person;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @NotNull
    private Issue issue;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status oldStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status newStatus;

    @NotNull
    private LocalDate logDate;
}
