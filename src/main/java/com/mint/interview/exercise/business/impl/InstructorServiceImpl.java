package com.mint.interview.exercise.business.impl;

import com.mint.interview.exercise.business.InstructorEventService;
import com.mint.interview.exercise.business.InstructorService;
import com.mint.interview.exercise.constants.CommonMessages;
import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.exception.BusinessException;
import com.mint.interview.exercise.exception.ResourceNotFoundException;
import com.mint.interview.exercise.mapper.InstructorMapper;
import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.InstructorDTO;
import com.mint.interview.exercise.model.InstructorPartialDTO;
import com.mint.interview.exercise.repository.InstructorRepository;
import com.mint.interview.exercise.utils.PartialUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repository;
    private final InstructorEventService instructorEventService;
    private final InstructorMapper mapper;

    @Override
    @Transactional
    public Instructor create(InstructorDTO event) {
        Instructor dbEntity = repository.save(mapper.mapToEntity(event));
        dbEntity.setEvents(instructorEventService.bulkInstructorAssignToEvent(dbEntity, event.getEventIds()));
        return dbEntity;
    }

    @Override
    public Instructor getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Instructor.RESOURCE_NAME, id.toString()));
    }

    @Override
    public List<Instructor> list() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Instructor update(UUID id, InstructorDTO event) {
        Instructor dbEntity = this.getById(id);

        mapper.update(event, dbEntity);
        dbEntity = repository.save(dbEntity);
        instructorEventService.unassignEvents(dbEntity.getEvents());
        dbEntity.setEvents(instructorEventService.bulkInstructorAssignToEvent(dbEntity, event.getEventIds()));
        return dbEntity;
    }

    @Override
    @Transactional
    public Instructor patch(UUID id, InstructorPartialDTO partial) {
        Instructor dbEntity = this.getById(id);

        mapper.patch(partial, dbEntity);
        dbEntity = repository.save(dbEntity);

        if (PartialUtils.validateOptionalPresent(partial.getEventIds()) &&
                validateDifferentUUIDsFromEventSet(dbEntity.getEvents(), partial.getEventIds().get())) {
            instructorEventService.unassignEvents(dbEntity.getEvents());
            dbEntity.setEvents(instructorEventService.bulkInstructorAssignToEvent(dbEntity, partial.getEventIds().get()));
        }

        return dbEntity;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Instructor instructor = this.getById(id);
        if (!CollectionUtils.isEmpty(instructor.getEvents())) {
            instructorEventService.unassignEvents(instructor.getEvents());
            /*throw new BusinessException(HttpStatus.BAD_REQUEST,
                    CommonMessages.OPERATION_NOT_ALLOWED, "Instructor has events assigned, delete them first");*/
        }
        repository.delete(instructor);
    }

    @Override
    @Transactional
    public Instructor assignEvent(UUID instructorId, UUID eventId) {
        Instructor instructor = this.getById(instructorId);
        instructor.getEvents().stream()
                .filter(event -> Objects.equals(event.getId(), eventId)).findAny()
                .ifPresent(e -> {
                    throw new BusinessException(HttpStatus.BAD_REQUEST,
                            CommonMessages.OPERATION_NOT_ALLOWED, "Event is already assigned to this Instructor");
                });

        Set<Event> events = instructorEventService.bulkInstructorAssignToEvent(instructor, Set.of(eventId));
        if (CollectionUtils.isEmpty(events)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    CommonMessages.OPERATION_NOT_ALLOWED, "Event is already assigned to another Instructor");
        }
        instructor.getEvents().addAll(events);
        return instructor;
    }

    @Override
    @Transactional
    public Instructor unassignEvent(UUID instructorId, UUID eventId) {
        Instructor instructor = this.getById(instructorId);
        Event oldEvent = instructor.getEvents().stream().filter(event -> Objects.equals(event.getId(), eventId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(Event.RESOURCE_NAME, eventId.toString()));
        instructorEventService.unassignEvents(Set.of(oldEvent));
        instructor.getEvents().remove(oldEvent);
        return instructor;
    }

    private boolean validateDifferentUUIDsFromEventSet(Set<Event> set, Set<UUID> uuids) {
        Set<UUID> currentIds = set.stream().map(Event::getId).collect(Collectors.toSet());
        return !uuids.containsAll(currentIds) || !currentIds.containsAll(uuids);
    }



}
