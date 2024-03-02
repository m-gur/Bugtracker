package org.mg.bugtracker.repository.project;

import org.mg.bugtracker.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findAll();

    @Query(value = """
            SELECT project
            FROM Project project
            WHERE project.name = :name
            """)
    Optional<Project> findByName(String name);

    @Modifying
    @Query(value = """
           UPDATE Project
           SET enabled = true
           WHERE projectId = :projectId
           """)
    void enableProjectById(int projectId);
}
