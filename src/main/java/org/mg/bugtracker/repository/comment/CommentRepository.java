package org.mg.bugtracker.repository.comment;

import org.mg.bugtracker.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAll();

    Optional<Comment> findCommentByCommentId(Integer commentId);
}
