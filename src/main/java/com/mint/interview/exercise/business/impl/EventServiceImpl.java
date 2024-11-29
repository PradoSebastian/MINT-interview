package com.mint.interview.exercise.business.impl;

import com.mint.interview.exercise.business.EventService;
import com.mint.interview.exercise.business.InstructorService;
import com.mint.interview.exercise.constants.CommonMessages;
import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.EventType;
import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.exception.BadRequestException;
import com.mint.interview.exercise.exception.BusinessException;
import com.mint.interview.exercise.exception.ResourceNotFoundException;
import com.mint.interview.exercise.mapper.EventMapper;
import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.EventPartialDTO;
import com.mint.interview.exercise.repository.EventRepository;
import com.mint.interview.exercise.repository.EventTypeRepository;
import com.mint.interview.exercise.utils.PagingUtils;
import com.mint.interview.exercise.utils.PartialUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    private final EventTypeRepository typeRepository;

    private final EventMapper mapper;

    private final InstructorService instructorService;

    @Override
    @Transactional
    public Event create(EventDTO event) {
        Event entity = mapper.mapToEntity(event);
        entity.setType(validateEventType(entity.getType()));

        if (Objects.nonNull(event.getInstructorId())) {
            validateInstructorAssignment(event.getInstructorId(), entity);
        }

        return repository.save(entity);
    }

    @Override
    public Event getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Event.RESOURCE_NAME, id.toString()));
    }

    @Override
    public Page<Event> list(String type, boolean unassigned, int page, int size, String[] sort) {
        if (page < 0) {
            throw new BadRequestException(CommonMessages.PARAM_INVALID, "page",
                    String.valueOf(page), "Page number cannot be negative");
        }
        if (size <= 0) {
            throw new BadRequestException(CommonMessages.PARAM_INVALID, "size",
                    String.valueOf(size), "Page size must be greater than 0");
        }

        Pageable pageable = PagingUtils.createPageable(page, size, sort);
        Specification<Event> specification = createSpecification(type, unassigned);
        Page<Event> list = repository.findAll(specification, pageable);

        if (page >= list.getTotalPages()) {
            throw new BadRequestException(CommonMessages.PARAM_INVALID, "page",
                    String.valueOf(page), "Page number exceeds total pages.");
        }
        return list;
    }

    @Override
    @Transactional
    public Event update(UUID id, EventDTO event) {
        Event dbEntity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Event.RESOURCE_NAME, id.toString()));
        EventType eventType = dbEntity.getType();

        if (!Objects.equals(dbEntity.getType().getType(), event.getType())) {
             eventType = validateEventType(new EventType(event.getType()));
        }
        mapper.update(event, dbEntity);
        dbEntity.setType(eventType);

        if (Objects.nonNull(event.getInstructorId()) && Objects.nonNull(dbEntity.getInstructor()) &&
                !Objects.equals(event.getInstructorId(), dbEntity.getInstructor().getId())) {
            validateInstructorAssignment(event.getInstructorId(), dbEntity);
        }
        return repository.save(dbEntity);
    }

    @Override
    @Transactional
    public Event patch(UUID id, EventPartialDTO partial) {
        Event dbEntity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Event.RESOURCE_NAME, id.toString()));
        EventType eventType = dbEntity.getType();

        if (PartialUtils.validateOptionalPresent(partial.getType()) &&
                !Objects.equals(dbEntity.getType().getType(), partial.getType().get())) {
            eventType = validateEventType(new EventType(partial.getType().get()));
        }

        mapper.patch(partial, dbEntity);
        dbEntity.setType(eventType);

        if (PartialUtils.validateOptionalPresent(partial.getInstructorId()) && (
                (Objects.isNull(dbEntity.getInstructor()) ||
                !Objects.equals(partial.getInstructorId().get(), dbEntity.getInstructor().getId())))) {
            validateInstructorAssignment(partial.getInstructorId().get(), dbEntity);
        }

        return repository.save(dbEntity);
    }

    @Override
    public void delete(UUID id) {
        Event event = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Event.RESOURCE_NAME, id.toString()));
        repository.delete(event);
    }

    @Override
    @Transactional
    public Event assignInstructor(UUID eventId, UUID instructorId) {
        Event event = this.getById(eventId);
        if (Objects.nonNull(event.getInstructor())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    CommonMessages.OPERATION_NOT_ALLOWED, "Event is already assigned");
        }
        event.setInstructor(instructorService.getById(instructorId));
        return repository.save(event);
    }

    @Override
    @Transactional
    public Event unassignInstructor(UUID eventId) {
        Event event = this.getById(eventId);
        //Maybe add login features in order to validate permissions
        event.setInstructor(null);
        return repository.save(event);
    }

    private EventType validateEventType(EventType eventType) {
        return Objects.nonNull(eventType.getType()) ? typeRepository.findByType(eventType.getType())
                .orElseGet(() -> typeRepository.save(eventType)) : null;
    }

    private Specification<Event> createSpecification(String type, boolean unassigned) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.join("type").get("type"), type));
            }
            if (unassigned) {
                predicates.add(criteriaBuilder.isNull(root.get("instructor")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void validateInstructorAssignment(UUID instructorId, Event entity) {
        Instructor instructor = instructorService.getById(instructorId);
        if (instructor.validateIfEventIsOverlapped(entity)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    CommonMessages.OPERATION_NOT_ALLOWED, "Event is overlapped with Instructor events");
        }
        entity.setInstructor(instructor);
    }
}
