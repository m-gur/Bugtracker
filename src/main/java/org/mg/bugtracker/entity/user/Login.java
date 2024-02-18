package org.mg.bugtracker.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE logins SET deleted = 1 WHERE login_id = ?", check = ResultCheckStyle.COUNT)
public class Login {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loginId;

    @NotNull
    private String login;

    @NotNull
    private byte[] password;

    @NotNull
    private String email;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @NotNull
    private Authority authority;

    @NotNull
    private boolean deleted;
}
