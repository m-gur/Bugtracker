package org.mg.bugtracker.repository.event;

import org.mg.bugtracker.entity.event.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Integer> {
}