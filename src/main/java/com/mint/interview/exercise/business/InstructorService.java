package com.mint.interview.exercise.business;

import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.model.InstructorDTO;
import com.mint.interview.exercise.model.InstructorPartialDTO;

import java.util.List;
import java.util.UUID;

public interface InstructorService {
    Instructor create(InstructorDTO event);

    Instructor getById(UUID id);

    List<Instructor> list();

    Instructor update(UUID id, InstructorDTO event);

    Instructor patch(UUID id, InstructorPartialDTO partial);

    void delete(UUID id);

    Instructor assignEvent(UUID instructorId, UUID eventId);

    Instructor unassignEvent(UUID instructorId, UUID eventId);
}
