package org.mg.bugtracker.configure.security;

import org.mg.bugtracker.entity.user.Login;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static org.mg.bugtracker.configure.LoginContextHolder.setContextLogin;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findLoginByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

        setContextLogin(login);
        String password = new String(login.getPassword(), StandardCharsets.UTF_8);
        return new User(login.getLogin(),
            password,
            true, 
            true, 
            true, 
            !login.isDeleted(), 
            AuthorityUtils.createAuthorityList(login.getAuthority().getName()));
    }
}