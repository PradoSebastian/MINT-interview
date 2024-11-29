package com.mint.interview.exercise.model;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
public class InstructorPartialDTO implements Serializable {

    private Optional<String> firstName;

    private Optional<String> lastName;

    private Optional<LocalDate> birthday;

    private Optional<Set<UUID>> eventIds;

}
