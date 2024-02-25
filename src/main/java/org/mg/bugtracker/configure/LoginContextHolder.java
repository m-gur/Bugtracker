package org.mg.bugtracker.configure;

import org.mg.bugtracker.entity.user.Login;
import org.springframework.stereotype.Component;

@Component
public class LoginContextHolder {
    private static Login login;
    public static Login getLoginFromContext() {
        return login;
    }

    public static void setContextLogin(Login login) {
        LoginContextHolder.login = login;
    }
}
