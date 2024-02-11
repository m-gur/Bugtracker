package org.mg.bugtracker.repository.issue;

import org.mg.bugtracker.entity.issue.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAll();
}
