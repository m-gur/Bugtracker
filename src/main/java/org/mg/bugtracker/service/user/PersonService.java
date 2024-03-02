package org.mg.bugtracker.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.mappers.user.PersonMapper;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.mg.bugtracker.repository.user.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.mg.bugtracker.configure.LoginContextHolder.getLoginFromContext;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final LoginService loginService;
    private final PersonMapper personMapper;
    private final LoginRepository loginRepository;

    public List<PersonDTO> getAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toPersonDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(int personId) {
        return personRepository.findById(personId)
                .map(personMapper::toPersonDTO)
                .orElseThrow(() -> new RuntimeException("Person with given id does not exist"));
    }

    public PersonDTO addPerson(RequestedPerson person) {
        Login newLogin = loginService.createLogin(person);
        Person newPerson = createPerson(person, newLogin);
        return personMapper.toPersonDTO(personRepository.save(newPerson));
    }

    @Transactional
    public void deletePerson(int personId) {
        loginRepository.deleteByPersonId(personId);
        personRepository.deleteById(personId);
    }

    public Person findPersonForContextLogin() {
        Login loginFromContext = getLoginFromContext();
        return personRepository.findPersonByLoginId(loginFromContext.getLoginId())
                .orElseThrow(() -> new RuntimeException("Cannot find person with existed login!"));
    }

    private Person createPerson(RequestedPerson requestedPerson, Login login) {
        Person newPerson = personMapper.fromRequest(requestedPerson);
        newPerson.setLogin(login);
        newPerson.setDeleted(false);
        return newPerson;
    }
}
