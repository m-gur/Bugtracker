package org.mg.bugtracker.entity.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int eventId;

    @NotNull
    private String ipAddress;

    @NotNull
    private String requestMethod;

    @NotNull
    private String requestUri;

    @NotNull
    private String user;

    @NotNull
    private LocalDateTime requestTimestamp;

}