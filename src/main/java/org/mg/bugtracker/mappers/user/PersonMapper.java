package org.mg.bugtracker.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;

@Mapper
public interface PersonMapper {

    @Mapping(target = "login", source = "loginId", qualifiedByName = "getLogin")
    Person toPerson(PersonDTO dto);

    @Mapping(target = "loginId", source = "login", qualifiedByName = "getLoginId")
    PersonDTO toPersonDTO(Person person);

    @Mapping(target = "login", ignore = true)
    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    Person fromRequest(RequestedPerson requestedPerson);

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
