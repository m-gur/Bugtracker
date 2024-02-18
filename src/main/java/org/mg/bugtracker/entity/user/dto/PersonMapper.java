package org.mg.bugtracker.entity.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.Person;

@Mapper
public interface PersonMapper {

    @Mapping(target = "login", source = "loginId", qualifiedByName = "getLogin")
    Person toPerson(PersonDTO dto);

    @Mapping(target = "loginId", source = "login", qualifiedByName = "getLoginId")
    PersonDTO toPersonDTO(Person person);

    @Named("getLogin")
    default Login getLogin(Integer loginId) {
        if (loginId != null) {
            Login login = new Login();
            login.setLoginId(loginId);
            return login;
        }
        return null;
    }

    @Named("getLoginId")
    default Integer getLoginId(Login login) {
        return (login != null) ? login.getLoginId() : null;
    }
}
