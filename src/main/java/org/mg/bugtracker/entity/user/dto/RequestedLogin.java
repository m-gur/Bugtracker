package org.mg.bugtracker.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestedLogin {
    private String login;
    private String password;
    private String email;
}
