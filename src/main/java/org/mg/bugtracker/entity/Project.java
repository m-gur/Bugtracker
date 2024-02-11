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
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    private String name;

    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    @NotNull
    private boolean enabled;

    @NotNull
    private LocalDate dateCreated;

    private String code;

    private String description;
}
