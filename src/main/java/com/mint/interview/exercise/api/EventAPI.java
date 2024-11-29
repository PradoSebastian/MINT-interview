package com.mint.interview.exercise.api;

import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.EventPartialDTO;
import com.mint.interview.exercise.model.PagedResponseDTO;
import com.mint.interview.exercise.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.mint.interview.exercise.api.EventAPI.BASE_URL;

@RequestMapping(BASE_URL)
public interface EventAPI {

    static final String BASE_URL = "/events";

    // TODO: check openAPI swagger
    //@Operation(method = "MINT01")
    @PostMapping
    ResponseEntity<EventDTO> create(@RequestBody EventDTO eventDto);

    //@Operation(method = "MINT02")
    @GetMapping("/{eventId}")
    ResponseEntity<EventDTO> getById(@PathVariable UUID eventId);

    //@Operation(method = "MINT03")
    @GetMapping
    ResponseEntity<PagedResponseDTO<EventDTO>> list(@RequestParam(required = false) String type,
                                                    @RequestParam(required = false) boolean unassigned,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "startDate,asc") String sort);

    //@Operation(method = "MINT04")
    @PutMapping("/{eventId}")
    ResponseEntity<EventDTO> update(@PathVariable UUID eventId, @RequestBody EventDTO eventDto);

    //@Operation(method = "MINT05")
    @PatchMapping("/{eventId}")
    ResponseEntity<EventDTO> patch(@PathVariable UUID eventId, @RequestBody EventPartialDTO partialEventDto);

    //@Operation(method = "MINT06")
    @DeleteMapping("/{eventId}")
    ResponseEntity<ResponseDTO> delete(@PathVariable UUID eventId);

    //@Operation(method = "MINT07")
    @PostMapping("/{eventId}/instructors/{instructorId}")
    ResponseEntity<EventDTO> assignInstructor(@PathVariable UUID eventId, @PathVariable UUID instructorId);

    //@Operation(method = "MINT08")
    @DeleteMapping("/{eventId}/instructors")
    ResponseEntity<EventDTO> unassignInstructor(@PathVariable UUID eventId);

}
