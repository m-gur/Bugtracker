package org.mg.bugtracker.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.PersonMapper;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.mg.bugtracker.repository.user.PersonRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private LoginService loginService;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private LoginRepository loginRepository;

    @Test
    void getAll_withoutParameters_returnsNotEmpty() {
        // given
        List<Person> personList = new ArrayList<>();
        personList.add(new Person());
        personList.add(new Person());

        // when
        when(personRepository.findAll()).thenReturn(personList);
        when(personMapper.toPersonDTO(any(Person.class))).thenReturn(new PersonDTO());
        List<PersonDTO> all = personService.getAll();

        // then
        assertEquals(2, all.size());
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        when(personRepository.findAll()).thenReturn(List.of());
        List<PersonDTO> all = personService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    void getPersonById_withoutParameters_returnsPerson() {
        // given
        Person person = new Person();
        person.setName("name");

        PersonDTO dto = new PersonDTO();
        dto.setName(person.getName());

        // when
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(person));
        when(personMapper.toPersonDTO(any(Person.class))).thenReturn(dto);
        PersonDTO personById = personService.getPersonById(anyInt());

        // then
        assertEquals(dto.getName(), personById.getName());
    }

    @Test
    void getPersonById_withoutParameters_ThrowsException() {
        // when
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> personService.getPersonById(anyInt()));
    }

    @Test
    void addPerson_withoutParameters_successAdd() {
        // given
        RequestedPerson requestedPerson = new RequestedPerson("name", "surname", "login", "password", "email@email.email", 1);

        PersonDTO dto = new PersonDTO();
        dto.setName(requestedPerson.getName());

        // when
        when(personMapper.toPersonDTO(any(Person.class))).thenReturn(dto);
        PersonDTO personDTO = personService.addPerson(requestedPerson);

        // then
        assertEquals(requestedPerson.getName(), personDTO.getName());
    }

    @Test
    void deletePerson_withoutParameters_successDelete() {
        // given
        int personId = 1;

        // when
        personService.deletePerson(personId);

        // then
        verify(loginRepository, times(1)).deleteByPersonId(anyInt());
        verify(personRepository, times(1)).deleteById(anyInt());
    }

}