package org.mg.bugtracker.repository.issue;

import org.mg.bugtracker.entity.issue.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAll();

    @Query(value = """
            SELECT tag
            FROM Tag tag
            WHERE tag.name = :name
            """)
    Optional<Tag> findByName(String name);
}
