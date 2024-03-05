package org.mg.bugtracker.service.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.event.EventLog;
import org.mg.bugtracker.entity.event.dto.CreateEventLog;
import org.mg.bugtracker.entity.event.dto.EventLogDto;
import org.mg.bugtracker.mappers.event.EventMapper;
import org.mg.bugtracker.repository.event.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventLogRepository eventLogRepository;
    private final EventMapper eventMapper;

    @Transactional
    public void save(CreateEventLog createEventLog) {
        EventLog newEventLog = eventMapper.toEventLog(createEventLog);
        eventLogRepository.save(newEventLog);
    }

    @Transactional
    public List<EventLogDto> getAllEventLogs() {
        return eventLogRepository.findAll()
                .stream()
                .map(eventMapper::toEventLogDTO)
                .collect(Collectors.toList());
    }
}