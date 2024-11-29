package com.mint.interview.exercise.business;

import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.EventPartialDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EventService {
    Event create(EventDTO event);

    Event getById(UUID id);

    Page<Event> list(String type, boolean unassigned, int page, int size, String[] sort);

    Event update(UUID id, EventDTO event);

    Event patch(UUID id, EventPartialDTO partial);

    void delete(UUID id);

    Event assignInstructor(UUID eventId, UUID instructorId);

    Event unassignInstructor(UUID eventId);
}
