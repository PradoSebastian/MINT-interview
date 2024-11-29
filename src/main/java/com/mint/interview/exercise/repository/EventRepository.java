package com.mint.interview.exercise.repository;

import com.mint.interview.exercise.domain.Event;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface EventRepository extends ListCrudRepository<Event, UUID>, JpaSpecificationExecutor<Event> {

    @Query(value = "SELECT e FROM Event e WHERE e.id IN :ids")
    Set<Event> findAllInUUIDList(Set<UUID> ids);

}
