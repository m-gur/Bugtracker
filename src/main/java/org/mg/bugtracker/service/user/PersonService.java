package org.mg.bugtracker.service.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.PersonMapper;
import org.mg.bugtracker.repository.user.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<PersonDTO> getAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toPersonDTO)
                .collect(Collectors.toList());
    }
}
