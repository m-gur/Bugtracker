package org.mg.bugtracker.configure;

import org.mg.bugtracker.entity.user.Login;
import org.springframework.stereotype.Component;

@Component
public class LoginContextHolder {
    private static final ThreadLocal<Login> context = new ThreadLocal<>();

    public static Login getLoginFromContext() {
        return context.get();
    }

    public static void setContextLogin(Login login) {
        context.set(login);
    }
}
