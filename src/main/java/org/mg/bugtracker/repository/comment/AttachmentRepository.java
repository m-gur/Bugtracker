package org.mg.bugtracker.repository.comment;

import org.mg.bugtracker.entity.comment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    List<Attachment> findAll();
}
