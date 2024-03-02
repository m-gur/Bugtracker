package org.mg.bugtracker.controller.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.dto.LoginDTO;
import org.mg.bugtracker.entity.user.dto.RequestedLogin;
import org.mg.bugtracker.service.user.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @PostMapping(value = "/add-login")
    public void addLogin(@RequestBody RequestedLogin requestedLogin) {
        loginService.createLoginWithDefaultAuthority(requestedLogin);
    }

    @GetMapping(value = "/bugtracker/logins/{loginId}")
    public LoginDTO getLoginById(@PathVariable int loginId) {
        return loginService.getLoginById(loginId);
    }
}
