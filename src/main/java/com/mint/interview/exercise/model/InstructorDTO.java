package com.mint.interview.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    @JsonIgnoreProperties("instructor")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<EventDTO> events = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UUID> eventIds = new HashSet<>();

    @JsonIgnoreProperties("instructor")
    public long getTotalOverallDurationDays() {
        return this.events.stream().map(EventDTO::getOverallDurationDays).reduce(Long::sum).orElse(0L);
    }

}
