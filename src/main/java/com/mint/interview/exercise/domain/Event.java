package com.mint.interview.exercise.domain;

import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    public static final String RESOURCE_NAME = "Event";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @PodamExclude
    private Instructor instructor;

    private String title;

    private String description;
}
