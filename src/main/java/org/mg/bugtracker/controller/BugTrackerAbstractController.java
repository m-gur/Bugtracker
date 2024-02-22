package org.mg.bugtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bugtracker")
public abstract class BugTrackerAbstractController {

    protected ObjectMapper objectMapper;

    public BugTrackerAbstractController() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}
