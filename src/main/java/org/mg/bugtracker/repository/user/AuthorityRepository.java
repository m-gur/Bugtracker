package org.mg.bugtracker.repository.user;

import org.mg.bugtracker.entity.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    List<Authority> findAll();

    Optional<Authority> findAuthorityByName(String name);

    @Query(value = """
            SELECT authority.name
            FROM Authority authority
            WHERE authority.authorityId = (
                SELECT login.authority.authorityId
                FROM Login login
                WHERE login.loginId = :loginId)
            """)
    String findAuthorityByLoginId(int loginId);

}
