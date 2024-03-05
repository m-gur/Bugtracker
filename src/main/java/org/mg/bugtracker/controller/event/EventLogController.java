package org.mg.bugtracker.controller.event;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.event.dto.EventLogDto;
import org.mg.bugtracker.service.event.EventLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/tracking")
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogService eventLogService;

    @GetMapping(path = "/events")
    public ResponseEntity<List<EventLogDto>> getAllLogs() {
        return ResponseEntity.ok(eventLogService.getAllEventLogs());
    }
}