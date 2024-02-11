package org.mg.bugtracker.entity.user.dto;

import org.mapstruct.Mapper;
import org.mg.bugtracker.entity.user.Authority;

@Mapper
public interface AuthorityMapper {

    Authority toAuthority(AuthorityDTO dto);

    AuthorityDTO toAuthorityDTO(Authority authority);
}
