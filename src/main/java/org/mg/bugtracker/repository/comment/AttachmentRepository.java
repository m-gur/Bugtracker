package org.mg.bugtracker.repository.comment;

import org.mg.bugtracker.entity.comment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    List<Attachment> findAll();

    @Modifying
    @Query("""
            UPDATE Attachment
            SET deleted = true
            WHERE comment.commentId = :commentId
            """)
    void deleteAttachmentsByCommentId(int commentId);

    @Modifying
    @Query("""
            UPDATE Attachment
            SET deleted = true
            WHERE comment.issue.issueId = :issueId
            """)
    void deleteAttachmentsByIssueId(int issueId);
}
