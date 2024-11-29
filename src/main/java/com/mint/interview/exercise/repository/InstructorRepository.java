package com.mint.interview.exercise.repository;

import com.mint.interview.exercise.domain.Instructor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstructorRepository extends ListCrudRepository<Instructor, UUID> {

}
