package org.mg.bugtracker.configure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.mg.bugtracker.configure.security.AuthorityList.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final HttpLoginSuccessHandler httpLoginSuccessHandler;
    private final HttpLoginFailureHandler httpLoginFailureHandler;
    private final HttpLogoutSuccessHandler httpLogoutSuccessHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(userDetailsService)
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .authorizeHttpRequests(customizer -> customizer

                //project
                .requestMatchers(HttpMethod.DELETE,"/bugtracker/projects/{projectId}").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.PUT,"/bugtracker/projects/{projectId}").hasAuthority(adminAuthority)
                .requestMatchers("/bugtracker/projects/add").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.GET,"/bugtracker/projects/{projectId}").hasAnyAuthority(anyAuthority)
                .requestMatchers("/bugtracker/projects/all").hasAnyAuthority(anyAuthority)

                //authorities
                .requestMatchers("/bugtracker/authorities/**").hasAuthority(adminAuthority)
                .requestMatchers("/bugtracker/default-authority").hasAnyAuthority(anyAuthority)

                //persons
                .requestMatchers("/bugtracker/persons/add").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.DELETE,"/bugtracker/persons/{personId}").hasAuthority(adminAuthority)
                .requestMatchers("/bugtracker/persons/all").hasAnyAuthority(anyAuthority)
                .requestMatchers(HttpMethod.GET,"/bugtracker/persons/{personId}").hasAnyAuthority(anyAuthority)

                //issues
                .requestMatchers("/bugtracker/issues/all",
                                 "/bugtracker/issues/add").hasAnyAuthority(anyAuthority)
                .requestMatchers(HttpMethod.GET,"/bugtracker/issues/{issueId}").hasAnyAuthority(anyAuthority)
                .requestMatchers(HttpMethod.DELETE,"/bugtracker/issues/{issueId}").hasAuthority(adminAuthority)

                //attachments
                .requestMatchers("/bugtracker/attachments/**").hasAnyAuthority(anyAuthority)

                //comments
                .requestMatchers("/bugtracker/comments/**").hasAnyAuthority(anyAuthority)

                //tags
                .requestMatchers("/bugtracker/tags/all").hasAnyAuthority(anyAuthority)
                .requestMatchers("/bugtracker/tags/add").hasAnyAuthority(extendedAuthority)
                .requestMatchers(HttpMethod.GET,"/bugtracker/tags/{tagId}").hasAnyAuthority(anyAuthority)
                .requestMatchers(HttpMethod.DELETE,"/bugtracker/tags/{tagId}").hasAnyAuthority(extendedAuthority)

                //logins
                .requestMatchers("/bugtracker/logins/{loginId}").hasAuthority(adminAuthority)

                //logs
                .requestMatchers("/bugtracker/issue-logs/all").hasAuthority(adminAuthority)

                //pages
                .requestMatchers("/add-project.html").hasAnyAuthority(extendedAuthority)
                .requestMatchers("/add-tag.html").hasAnyAuthority(extendedAuthority)
                .requestMatchers("/update-issue.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/add-comment.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/add-issue.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/projects.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/comments.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/issues.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/add-authority.html").hasAuthority(adminAuthority)
                .requestMatchers("/authorities.html").hasAuthority(adminAuthority)
                .requestMatchers("/add-person.html").hasAuthority(adminAuthority)
                .requestMatchers("/persons.html").hasAuthority(adminAuthority)
                .requestMatchers("/logs.html").hasAuthority(adminAuthority)

                //panels
                .requestMatchers("/user-panel.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/admin-panel.html").hasAuthority(adminAuthority)

                //add login
                .requestMatchers("/add-login.html").permitAll()
                .requestMatchers(HttpMethod.POST,"/add-login").permitAll()
                .anyRequest().authenticated()
                )

                .formLogin(customizer -> customizer
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .successHandler(httpLoginSuccessHandler)
                        .failureHandler(httpLoginFailureHandler))

                .logout(customizer -> customizer
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(httpLogoutSuccessHandler)
                        .invalidateHttpSession(true))

                .build();
    }
}