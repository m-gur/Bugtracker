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
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE persons SET deleted = 1 WHERE person_id = ?", check = ResultCheckStyle.COUNT)
public class Person {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    private String name;

    private String surname;

    @OneToOne
    @JoinColumn(name = "login_id")
    @NotNull
    private Login login;

    @NotNull
    private boolean deleted;
}
