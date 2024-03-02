package org.mg.bugtracker.entity.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.mg.bugtracker.entity.issue.Issue;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE projects SET enabled = 0 WHERE project_id = ?", check = ResultCheckStyle.COUNT)
public class Project {

    @Id
    @Column(unique = true)
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
