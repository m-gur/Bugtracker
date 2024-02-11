package org.mg.bugtracker.entity.issue;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issue_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueTag {

    @EmbeddedId
    private IssueTagId id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @MapsId("issueId")
    @NotNull
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @MapsId("tagId")
    @NotNull
    private Tag tag;
}
