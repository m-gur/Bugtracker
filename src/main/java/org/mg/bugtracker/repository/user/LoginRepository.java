package org.mg.bugtracker.repository.user;

import jakarta.validation.constraints.NotNull;
import org.mg.bugtracker.entity.user.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Integer> {

    Optional<Login> findLoginByLogin(@NotNull String login);
}
