package com.mint.interview.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String type;

    private String title;

    private String description;

    @JsonIgnoreProperties("events")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private InstructorDTO instructor;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID instructorId;

    public long getOverallDurationDays() {
        return ChronoUnit.DAYS.between(this.startDate, this.endDate);
    }

}
