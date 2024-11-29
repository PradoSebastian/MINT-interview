package com.mint.interview.exercise.domain;

import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "instructor")
public class Instructor implements Serializable {

    public static final String RESOURCE_NAME = "Instructor";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(columnDefinition = "DATE")
    private LocalDate birthday;

    @OneToMany(mappedBy="instructor")
    @PodamExclude
    private Set<Event> events = new HashSet<>();

    public boolean validateIfEventIsOverlapped(Event newEvent) {
        if (Objects.nonNull(events)) {
            for (Event event : this.events) {
                if (DateUtils.isOverlapping(newEvent.getStartDate(), newEvent.getEndDate(),
                        event.getStartDate(), event.getEndDate())) {
                    return true;
                }
            }
        }
        return false;
    }
}
