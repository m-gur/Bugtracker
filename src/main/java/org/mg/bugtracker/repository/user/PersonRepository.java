package org.mg.bugtracker.repository.user;

import org.mg.bugtracker.entity.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findAll();

    @Query(value = """
            SELECT person
            FROM Person person
            WHERE person.login.loginId = :loginId
            """)
    Optional<Person> findPersonByLoginId(int loginId);
}
