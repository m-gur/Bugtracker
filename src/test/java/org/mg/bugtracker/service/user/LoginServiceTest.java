package org.mg.bugtracker.service.user;

import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.user.Authority;
import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.entity.user.dto.LoginDTO;
import org.mg.bugtracker.entity.user.dto.RequestedLogin;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.mappers.user.LoginMapper;
import org.mg.bugtracker.repository.user.AuthorityRepository;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @InjectMocks
    private LoginService loginService;
    @Mock
    private LoginRepository loginRepository;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private LoginMapper loginMapper;

    @Test
    void createLoginWithDefaultAuthority_withoutParameters_successfulCreatedLogin() {
        // given
        RequestedLogin requestedLogin = new RequestedLogin();
        requestedLogin.setPassword("password");

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.empty());
        when(authorityRepository.findAuthorityByName(anyString())).thenReturn(Optional.of(new Authority(1, "USER", false)));
        loginService.createLoginWithDefaultAuthority(requestedLogin);

        // then
        verify(loginRepository, times(1)).save(any(Login.class));
    }

    @Test
    void createLoginWithDefaultAuthority_withoutParameters_failureOnLogin() {
        // given
        RequestedLogin requestedLogin = new RequestedLogin();

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.of(new Login()));

        // then
        assertThrows(EntityExistsException.class, () -> loginService.createLoginWithDefaultAuthority(requestedLogin));
    }

    @Test
    void createLoginWithDefaultAuthority_withoutParameters_failureOnAuthority() {
        // given
        RequestedLogin requestedLogin = new RequestedLogin();

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.empty());
        when(authorityRepository.findAuthorityByName(anyString())).thenReturn(Optional.empty());
        // then
        assertThrows(RuntimeException.class, () -> loginService.createLoginWithDefaultAuthority(requestedLogin));
    }

    @Test
    void createLogin_withoutParameters_successCreatedLogin() {
        // given
        RequestedPerson requestedPerson = new RequestedPerson();
        requestedPerson.setLogin("login");
        requestedPerson.setPassword("password");

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.empty());
        when(authorityRepository.findById(anyInt())).thenReturn(Optional.of(new Authority(1, "USER", false)));
        Login login = loginService.createLogin(requestedPerson);

        // then
        assertEquals(requestedPerson.getLogin(), login.getLogin());
        verify(loginRepository, times(1)).save(any(Login.class));
    }

    @Test
    void createLogin_withoutParameters_failureOnLogin() {
        // given
        RequestedPerson requestedPerson = new RequestedPerson();
        requestedPerson.setLogin("login");
        requestedPerson.setPassword("password");

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.of(new Login()));

        // then
        assertThrows(EntityExistsException.class, () -> loginService.createLogin(requestedPerson));
    }

    @Test
    void createLogin_withoutParameters_failureOnAuthority() {
        // given
        RequestedPerson requestedPerson = new RequestedPerson();
        requestedPerson.setLogin("login");
        requestedPerson.setPassword("password");

        // when
        when(loginRepository.findLoginByLogin(any())).thenReturn(Optional.empty());
        when(authorityRepository.findById(anyInt())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> loginService.createLogin(requestedPerson));
    }

    @Test
    void getLoginById_withoutParameters_returnsEqualsLogin() {
        // given
        int loginId = 1;
        String userLogin = "admin";

        Login login = new Login();
        login.setLoginId(1);
        login.setLogin(userLogin);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(userLogin);

        // when
        when(loginRepository.findById(loginId)).thenReturn(Optional.of(login));
        when(loginMapper.toLoginDTO(any(Login.class))).thenReturn(loginDTO);
        LoginDTO foundLogin = loginService.getLoginById(loginId);

        // then
        assertEquals(userLogin, foundLogin.getLogin());
    }

    @Test
    void getLoginById_withoutParameters_throwsException() {
        // when
        when(loginRepository.findById(anyInt())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> loginService.getLoginById(anyInt()));
    }
}