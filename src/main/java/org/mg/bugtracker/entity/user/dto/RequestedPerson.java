package org.mg.bugtracker.entity.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestedPerson {
    private String name;

    private String surname;

    private String login;

    private String password;

    private String email;

    private int authorityId;
}
