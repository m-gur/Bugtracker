package org.mg.bugtracker.repository.issue;

import org.mg.bugtracker.entity.issue.IssueTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueTagRepository extends JpaRepository<IssueTag, Integer> {
}
