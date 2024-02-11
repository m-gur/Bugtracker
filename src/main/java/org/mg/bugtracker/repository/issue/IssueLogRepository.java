package org.mg.bugtracker.repository.issue;

import org.mg.bugtracker.entity.issue.IssueLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueLogRepository extends JpaRepository<IssueLog, Integer> {

    List<IssueLog> findAll();
}
