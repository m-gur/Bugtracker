package org.mg.bugtracker.controller;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.dto.RequestedLogin;
import org.mg.bugtracker.service.user.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @PostMapping(value = "/add-login")
    public void addLogin(@RequestBody RequestedLogin requestedLogin) {
        loginService.createLoginWithDefaultAuthority(requestedLogin);
    }
}
