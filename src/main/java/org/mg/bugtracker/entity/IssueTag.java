package org.mg.bugtracker.entity;

import jakarta.persistence.*;
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
    @MapsId("issueId")
    private Issue issue;

    @ManyToOne
    @MapsId("tagId")
    private Tag tag;
}
