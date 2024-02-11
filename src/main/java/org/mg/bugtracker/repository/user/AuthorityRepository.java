package org.mg.bugtracker.repository.user;

import org.mg.bugtracker.entity.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    List<Authority> findAll();
}
