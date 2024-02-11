package org.mg.bugtracker.controller.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.service.user.AuthorityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorityController extends BugTrackerAbstractController {

    private final AuthorityService authorityService;

    @GetMapping(value = "authorities/all")
    public List<AuthorityDTO> getAll() {
        return authorityService.getAll();
    }
}
