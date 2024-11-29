package com.mint.interview.exercise.repository;

import com.mint.interview.exercise.domain.EventType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventTypeRepository extends ListCrudRepository<EventType, UUID> {
    Optional<EventType> findByType(String type);
}
