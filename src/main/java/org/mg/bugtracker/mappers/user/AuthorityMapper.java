package org.mg.bugtracker.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mg.bugtracker.entity.user.Authority;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.RequestedAuthority;

@Mapper
public interface AuthorityMapper {

    Authority toAuthority(AuthorityDTO dto);

    AuthorityDTO toAuthorityDTO(Authority authority);

    @Mapping(target = "authorityId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Authority fromAuthorityRequest(RequestedAuthority requestedAuthority);
}
