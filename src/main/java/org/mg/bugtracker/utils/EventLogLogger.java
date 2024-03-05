package org.mg.bugtracker.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.event.dto.CreateEventLog;
import org.mg.bugtracker.service.event.EventLogService;
import org.mg.bugtracker.service.user.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventLogLogger implements HandlerInterceptor {

    private final EventLogService eventLogService;
    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NotNull Object handler) {

        CreateEventLog eventLog = new CreateEventLog();

        eventLog.setIpAddress(request.getRemoteAddr());
        eventLog.setRequestMethod(request.getMethod());
        eventLog.setRequestUri(String.valueOf(request.getRequestURL()));
        eventLog.setRequestTimestamp(LocalDateTime.now());

        String login = request.getUserPrincipal().getName();
        String email = loginService.findByLogin(login).getEmail();
        eventLog.setUser("[" + login + "]: " + email);

        eventLogService.save(eventLog);

        return true;
    }
}