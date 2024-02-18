package org.mg.bugtracker.repository.user;

import jakarta.validation.constraints.NotNull;
import org.mg.bugtracker.entity.user.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Integer> {

    Optional<Login> findLoginByLogin(@NotNull String login);

    @Modifying
    @Query("""
            UPDATE Login login
            SET login.authority = null
            WHERE login.authority.authorityId = :authorityId
            """)
    void deleteByAuthorityId(int authorityId);

    @Modifying
    @Query("""
            UPDATE Login login
            SET login.deleted = true
            WHERE login.loginId = (
                SELECT person.login.loginId FROM Person person
                WHERE person.personId = :personId)
            """)
    void deleteByPersonId(int personId);

}
