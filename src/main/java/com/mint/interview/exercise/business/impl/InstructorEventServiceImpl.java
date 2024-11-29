package com.mint.interview.exercise.business.impl;

import com.mint.interview.exercise.business.InstructorEventService;
import com.mint.interview.exercise.business.InstructorService;
import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.exception.ResourceNotFoundException;
import com.mint.interview.exercise.mapper.InstructorMapper;
import com.mint.interview.exercise.model.InstructorDTO;
import com.mint.interview.exercise.model.InstructorPartialDTO;
import com.mint.interview.exercise.repository.EventRepository;
import com.mint.interview.exercise.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorEventServiceImpl implements InstructorEventService {

    private final InstructorRepository instructorRepository;
    private final EventRepository eventRepository;

    @Override
    public Set<Event> bulkInstructorAssignToEvent(Instructor instructor, Set<UUID> ids) {
        Set<Event> eventsByIds = findEventsByIds(ids);

        // Only add non-assigned events that are not overlapped
        Set<Event> assignedEvents = eventsByIds.stream().filter(event -> Objects.isNull(event.getInstructor()))
                .filter(event -> !instructor.validateIfEventIsOverlapped(event))
                .peek(event -> event.setInstructor(instructor))
                .map(eventRepository::save)
                .collect(Collectors.toSet());

        // Add already existing ones
        assignedEvents.addAll(
                eventsByIds.stream().filter(event -> Objects.nonNull(event.getInstructor()) &&
                                Objects.equals(event.getInstructor().getId(), instructor.getId()))
                    .peek(event -> event.setInstructor(instructor))
                    .map(eventRepository::save)
                    .collect(Collectors.toSet()));
        return assignedEvents;
    }

    @Override
    public void unassignEvents(Set<Event> events) {
        events.stream().peek(event -> event.setInstructor(null))
                .forEach(eventRepository::save);
    }

    private Set<Event> findEventsByIds(Set<UUID> ids) {
        return eventRepository.findAllInUUIDList(ids);
    }
}
