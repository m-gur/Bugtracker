package org.mg.bugtracker.repository.issue;

import org.mg.bugtracker.entity.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findAll();
}
