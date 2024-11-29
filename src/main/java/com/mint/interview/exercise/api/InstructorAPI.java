package com.mint.interview.exercise.api;

import com.mint.interview.exercise.model.InstructorDTO;
import com.mint.interview.exercise.model.InstructorPartialDTO;
import com.mint.interview.exercise.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.mint.interview.exercise.api.InstructorAPI.BASE_URL;

@RequestMapping(BASE_URL)
public interface InstructorAPI {

    static final String BASE_URL = "/instructors";

    // TODO: check openAPI swagger
    //@Operation(method = "MINT09")
    @PostMapping
    ResponseEntity<InstructorDTO> create(@RequestBody InstructorDTO instructorDto);

    //@Operation(method = "MINT10")
    @GetMapping("/{instructorId}")
    ResponseEntity<InstructorDTO> getById(@PathVariable UUID instructorId);

    //@Operation(method = "MINT11")
    @GetMapping
    ResponseEntity<List<InstructorDTO>> list();

    //@Operation(method = "MINT12")
    @PutMapping("/{instructorId}")
    ResponseEntity<InstructorDTO> update(@PathVariable UUID instructorId, @RequestBody InstructorDTO instructorDto);

    //@Operation(method = "MINT13")
    @PatchMapping("/{instructorId}")
    ResponseEntity<InstructorDTO> patch(@PathVariable UUID instructorId,
                                        @RequestBody InstructorPartialDTO partialInstructorDto);

    //@Operation(method = "MINT14")
    @DeleteMapping("/{instructorId}")
    ResponseEntity<ResponseDTO> delete(@PathVariable UUID instructorId);

    //@Operation(method = "MINT15")
    @PostMapping("/{instructorId}/events/{eventId}")
    ResponseEntity<InstructorDTO> assignEvent(@PathVariable UUID instructorId, @PathVariable UUID eventId);

    //@Operation(method = "MINT16")
    @DeleteMapping("/{instructorId}/events/{eventId}")
    ResponseEntity<InstructorDTO> unassignEvent(@PathVariable UUID instructorId, @PathVariable UUID eventId);

}
