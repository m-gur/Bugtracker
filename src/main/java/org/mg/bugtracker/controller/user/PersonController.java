package org.mg.bugtracker.controller.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.service.user.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController extends BugTrackerAbstractController {

    private final PersonService personService;

    @GetMapping(value = "persons/all")
    public List<PersonDTO> getAll() {
        return personService.getAll();
    }
}
