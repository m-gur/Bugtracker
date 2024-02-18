package org.mg.bugtracker.controller.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.AuthorityRequest;
import org.mg.bugtracker.service.user.AuthorityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorityController extends BugTrackerAbstractController {

    private final AuthorityService authorityService;

    @GetMapping(value = "/authorities/all")
    public List<AuthorityDTO> getAll() {
        return authorityService.getAll();
    }

    @GetMapping(value = "/authorities/{authorityId}")
    public AuthorityDTO getById(@PathVariable int authorityId) {
        return authorityService.getAuthorityById(authorityId);
    }

    @PostMapping(value = "/authorities/add")
    public AuthorityDTO addAuthority(@RequestBody AuthorityRequest authorityRequest) {
        return authorityService.addAuthority(authorityRequest);
    }

    @DeleteMapping(value = "/authorities/{authorityId}")
    public void deleteAuthority(@PathVariable int authorityId) {
        authorityService.deleteAuthority(authorityId);
    }
}
