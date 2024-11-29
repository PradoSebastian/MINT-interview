package com.mint.interview.exercise.controller;

import com.mint.interview.exercise.api.InstructorAPI;
import com.mint.interview.exercise.business.InstructorService;
import com.mint.interview.exercise.mapper.InstructorMapper;
import com.mint.interview.exercise.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InstructorController implements InstructorAPI {

    private final InstructorService service;
    private final InstructorMapper mapper;

    @Override
    public ResponseEntity<InstructorDTO> create(InstructorDTO instructorDto) {
        log.info("POST Request received for Create Instructor");
        InstructorDTO result = mapper.mapToDTO(service.create(instructorDto));
        log.info("POST Request finished successfully for Create Instructor {}", result.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public ResponseEntity<InstructorDTO> getById(UUID instructorId) {
        log.info("GET Request received for Get Instructor By Id {}", instructorId);
        InstructorDTO result = mapper.mapToDTO(service.getById(instructorId));
        log.info("GET Request finished successfully for Get Instructor By Id {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<InstructorDTO>> list() {
        log.info("GET Request received for List Instructors");
        List<InstructorDTO> instructors = service.list().stream().map(mapper::mapToDTO).toList();
        log.info("GET Request finished successfully for List Instructors");
        return ResponseEntity.ok(instructors);

    }

    @Override
    public ResponseEntity<InstructorDTO> update(UUID instructorId, InstructorDTO instructorDto) {
        log.info("PUT Request received for Update Instructor {}", instructorId);
        InstructorDTO result = mapper.mapToDTO(service.update(instructorId, instructorDto));
        log.info("PUT Request finished successfully for Update Instructor {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<InstructorDTO> patch(UUID instructorId, InstructorPartialDTO partialInstructorDto) {
        log.info("PATCH Request received for Update Partial Instructor {}", instructorId);
        InstructorDTO result = mapper.mapToDTO(service.patch(instructorId, partialInstructorDto));
        log.info("PATCH Request finished successfully for Update Partial Instructor {}", result.getId());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ResponseDTO> delete(UUID instructorId) {
        log.info("DELETE Request received for Delete Instructor {}", instructorId);
        service.delete(instructorId);
        log.info("DELETE Request finished successfully for Delete Instructor {}", instructorId);
        return ResponseEntity.ok(new ResponseDTO(true));
    }

    @Override
    public ResponseEntity<InstructorDTO> assignEvent(UUID instructorId, UUID eventId) {
        log.info("POST Request received for Assign Event {} to Instructor {}", eventId, instructorId);
        InstructorDTO result = mapper.mapToDTO(service.assignEvent(eventId, instructorId));
        log.info("POST Request finished successfully for Assign Event {} to Instructor {}", eventId, instructorId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<InstructorDTO> unassignEvent(UUID instructorId, UUID eventId) {
        log.info("DELETE Request received for Unassign Event {} from Instructor {}", eventId, instructorId);
        InstructorDTO result = mapper.mapToDTO(service.unassignEvent(instructorId, eventId));
        log.info("DELETE Request finished successfully for Unassign Event {} from Instructor {}", eventId, instructorId);
        return ResponseEntity.ok(result);
    }
}
