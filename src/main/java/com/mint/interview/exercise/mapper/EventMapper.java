package com.mint.interview.exercise.mapper;

import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.EventType;
import com.mint.interview.exercise.model.EventDTO;
import com.mint.interview.exercise.model.EventPartialDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {InstructorMapper.class, CommonMapper.class})
public interface EventMapper {

    @Mapping(target = "instructor", ignore = true)
    Event mapToEntity(EventDTO dto);

    @Mapping(target = "type", source = "type.type")
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "instructor", source = "instructor", qualifiedByName = "mapToDTONonCyclic")
    EventDTO mapToDTO(Event entity);

    @Mapping(target = "type", source = "type.type")
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Named("mapToDTONonCyclic")
    EventDTO mapToDTONonCyclic(Event entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    void update(EventDTO eventDTO, @MappingTarget Event entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patch(EventPartialDTO partialDTO, @MappingTarget Event entity);

    default EventType convertEventType(String type) {
        return EventType.builder().type(type).build();
    }
}
