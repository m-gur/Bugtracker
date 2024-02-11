package org.mg.bugtracker.entity.issue;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<IssueTag> issueTags;

    @NotNull
    private boolean deleted;
}
