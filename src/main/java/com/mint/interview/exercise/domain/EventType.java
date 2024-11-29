package com.mint.interview.exercise.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_type")
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique=true)
    private String type;

    public EventType(String type) {
        this.type = type;
    }
}
