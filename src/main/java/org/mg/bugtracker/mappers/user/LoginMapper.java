package org.mg.bugtracker.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.dto.LoginDTO;

@Mapper
public interface LoginMapper {
    @Mapping(target = "login", source = "login")
    LoginDTO toLoginDTO(Login login);
}
