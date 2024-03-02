package org.mg.bugtracker.configure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (request.isUserInRole("ADMIN")) {
            super.handle(request, response, accessDeniedException);
        } else {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request, response);

            if (savedRequest != null) {
                response.sendRedirect(savedRequest.getRedirectUrl());
            } else {
                super.handle(request, response, accessDeniedException);
            }
        }
    }
}