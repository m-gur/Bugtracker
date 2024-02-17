package org.mg.bugtracker.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeHttpRequests(customizer -> customizer
                .requestMatchers("/bugtracker/authorities/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())

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

                .csrf().disable()
                .build();
    }
}