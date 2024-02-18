package org.mg.bugtracker.service.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.PersonMapper;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.mg.bugtracker.repository.user.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Person newPerson = new Person();
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        newPerson.setLogin(newLogin);
        newPerson.setDeleted(false);
        personRepository.save(newPerson);

        return personMapper.toPersonDTO(newPerson);
    }

    public void deletePerson(int personId) {
        loginRepository.deleteByPersonId(personId);
        personRepository.deleteById(personId);
    }
}
