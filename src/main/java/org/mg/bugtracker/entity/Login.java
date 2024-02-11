package org.mg.bugtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    private Integer loginId;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @NotNull
    private Authority authority;

    @NotNull
    private boolean deleted;
}
