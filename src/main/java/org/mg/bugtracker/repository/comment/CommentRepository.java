package org.mg.bugtracker.repository.comment;

import org.mg.bugtracker.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAll();

    @Modifying
    @Query(value = """
            UPDATE Comment comment
            SET comment.deleted = true
            WHERE comment.issue.issueId = :issueId
            """)
    void deleteByIssueId(int issueId);
}
