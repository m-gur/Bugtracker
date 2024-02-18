package org.mg.bugtracker.controller.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.service.user.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController extends BugTrackerAbstractController {

    private final PersonService personService;

    @GetMapping(value = "/persons/all")
    public List<PersonDTO> getAll() {
        return personService.getAll();
    }

    @GetMapping(value = "/persons/{personId}")
    public PersonDTO getPersonById(@PathVariable int personId) {
        return personService.getPersonById(personId);
    }

    @PostMapping(value = "/persons/add")
    public PersonDTO addPerson(@RequestBody RequestedPerson person) {
        return personService.addPerson(person);
    }

    @DeleteMapping(value = "/persons/{personId}")
    public void deletePerson(@PathVariable int personId) {
        personService.deletePerson(personId);
    }
}
