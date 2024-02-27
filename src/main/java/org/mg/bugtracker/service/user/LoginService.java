package org.mg.bugtracker.service.user;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.Authority;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.dto.RequestedLogin;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.repository.user.AuthorityRepository;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthorityRepository authorityRepository;
    private final LoginRepository loginRepository;

    public void createLoginWithDefaultAuthority(RequestedLogin login) {
        Optional<Login> foundLogin = loginRepository.findLoginByLogin(login.getLogin());
        if (foundLogin.isEmpty()) {
            Authority authority = authorityRepository.findAuthorityByName("USER").orElseThrow(() -> new RuntimeException("Authority with requested name does not exist!"));
            Login newLogin = new Login();
            newLogin.setLogin(login.getLogin());
            newLogin.setPassword(BCrypt.hashpw(login.getPassword(), BCrypt.gensalt()).getBytes());
            newLogin.setEmail(login.getEmail());
            newLogin.setAuthority(authority);
            newLogin.setDeleted(false);
            loginRepository.save(newLogin);
        } else throw new EntityExistsException("Cannot create login!");
    }

    public Login createLogin(RequestedPerson person) {
        Optional<Login> foundLogin = loginRepository.findLoginByLogin(person.getLogin());
        if (foundLogin.isEmpty()) {
            Authority authority = authorityRepository.findById(person.getAuthorityId()).orElseThrow(() -> new RuntimeException("Authority with requested id does not exist!"));
            Login newLogin = new Login();
            newLogin.setLogin(person.getLogin());
            newLogin.setPassword(BCrypt.hashpw(person.getPassword(), BCrypt.gensalt()).getBytes());
            newLogin.setEmail(person.getEmail());
            newLogin.setAuthority(authority);
            newLogin.setDeleted(false);
            loginRepository.save(newLogin);
            return newLogin;
        }
        else throw new EntityExistsException("Cannot create login!");
    }
}
