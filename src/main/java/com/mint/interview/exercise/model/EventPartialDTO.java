package com.mint.interview.exercise.model;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
public class EventPartialDTO implements Serializable {

    private Optional<LocalDateTime> startDate;

    private Optional<LocalDateTime> endDate;

    private Optional<String> type;

    private Optional<String> title;

    private Optional<String> description;

    private Optional<UUID> instructorId;

}
