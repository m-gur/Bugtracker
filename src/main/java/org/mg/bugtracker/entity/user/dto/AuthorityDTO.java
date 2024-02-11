package org.mg.bugtracker.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDTO {

    private Integer authorityId;

    private String name;

    private boolean deleted;
}
