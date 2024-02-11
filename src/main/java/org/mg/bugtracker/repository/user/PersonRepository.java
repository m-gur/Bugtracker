package org.mg.bugtracker.repository.user;

import org.mg.bugtracker.entity.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findAll();
}
