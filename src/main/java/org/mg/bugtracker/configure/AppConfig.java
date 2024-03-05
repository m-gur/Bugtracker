package org.mg.bugtracker.configure;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.utils.EventLogLogger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final EventLogLogger eventLogLogger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(eventLogLogger).addPathPatterns("/bugtracker/**");
    }

}