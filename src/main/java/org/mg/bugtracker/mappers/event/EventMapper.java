package org.mg.bugtracker.mappers.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mg.bugtracker.entity.event.EventLog;
import org.mg.bugtracker.entity.event.dto.CreateEventLog;
import org.mg.bugtracker.entity.event.dto.EventLogDto;

@Mapper
public interface EventMapper {

    @Mapping(target = "eventId", ignore = true)
    EventLog toEventLog(CreateEventLog createEventLog);

    EventLogDto toEventLogDTO(EventLog eventLog);
}
