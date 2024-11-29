package com.mint.interview.exercise.controller;

import com.mint.interview.exercise.api.EventAPI;
import com.mint.interview.exercise.business.EventService;
import com.mint.interview.exercise.mapper.EventMapper;
import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.EventPartialDTO;
import com.mint.interview.exercise.model.PagedResponseDTO;
import com.mint.interview.exercise.model.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventController implements EventAPI {

    private final EventService service;
    private final EventMapper mapper;

    @Override
    public ResponseEntity<EventDTO> create(EventDTO eventDto) {
        log.info("POST Request received for Create Event");
        EventDTO result = mapper.mapToDTO(service.create(eventDto));
        log.info("POST Request finished successfully for Create Event {}", result.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<EventDTO> getById(UUID eventId) {
        log.info("GET Request received for Get Event By Id {}", eventId);
        EventDTO result = mapper.mapToDTO(service.getById(eventId));
        log.info("GET Request finished successfully for Get Event By Id {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<PagedResponseDTO<EventDTO>> list(String type, boolean unassigned, int page, int size, String sort) {
        log.info("GET Request received for List Events... type: {}, unassigned: {}, page: {}, size: {}, sort: {}",
                type, unassigned, page, size, sort);
        Page<EventDTO> events = service.list(type, unassigned, page, size, sort.split(",")).map(mapper::mapToDTO);
        log.info("GET Request finished successfully for List Events... type: {}, unassigned: {}, page: {}, size: {}, sort: {}",
                type, unassigned, page, size, sort);
        return ResponseEntity.ok(new PagedResponseDTO<>(events, sort, page, size));
    }

    @Override
    public ResponseEntity<EventDTO> update(UUID eventId, EventDTO eventDto) {
        log.info("PUT Request received for Update Event {}", eventId);
        EventDTO result = mapper.mapToDTO(service.update(eventId, eventDto));
        log.info("PUT Request finished successfully for Update Event {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventDTO> patch(UUID eventId, EventPartialDTO partialEventDto) {
        log.info("PATCH Request received for Update Partial Event {}", eventId);
        EventDTO result = mapper.mapToDTO(service.patch(eventId, partialEventDto));
        log.info("PATCH Request finished successfully for Update Partial Event {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(UUID eventId) {
        log.info("DELETE Request received for Delete Event {}", eventId);
        service.delete(eventId);
        log.info("DELETE Request finished successfully for Delete Event {}", eventId);
        return ResponseEntity.ok(new ResponseDTO(true));
    }

    @Override
    public ResponseEntity<EventDTO> assignInstructor(UUID eventId, UUID instructorId) {
        log.info("POST Request received for Assign Instructor {} to Event {}", instructorId, eventId);
        EventDTO result = mapper.mapToDTO(service.assignInstructor(eventId, instructorId));
        log.info("POST Request finished successfully for Assign Instructor {} to Event {}", instructorId, eventId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventDTO> unassignInstructor(UUID eventId) {
        log.info("DELETE Request received for unassign Instructor of Event {}", eventId);
        EventDTO result = mapper.mapToDTO(service.unassignInstructor(eventId));
        log.info("DELETE Request finished successfully for unassign Instructor of Event {}", eventId);
        return ResponseEntity.ok(result);
    }
}
