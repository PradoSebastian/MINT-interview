package com.mint.interview.exercise.business;

import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.Instructor;

import java.util.Set;
import java.util.UUID;

public interface InstructorEventService {
    Set<Event> bulkInstructorAssignToEvent(Instructor instructor, Set<UUID> ids);
    void unassignEvents(Set<Event> events);
}
