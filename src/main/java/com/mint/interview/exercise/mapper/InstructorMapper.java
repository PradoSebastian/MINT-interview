package com.mint.interview.exercise.mapper;

import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.model.InstructorDTO;
import com.mint.interview.exercise.model.InstructorPartialDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EventMapper.class, CommonMapper.class})
public interface InstructorMapper {

    @Mapping(target = "events", ignore = true)
    Instructor mapToEntity(InstructorDTO dto);

    @Mapping(target = "eventIds", ignore = true)
    @Mapping(target = "events", source = "events", qualifiedByName = "mapToDTONonCyclic")
    InstructorDTO mapToDTO(Instructor entity);

    @Mapping(target = "eventIds", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Named("mapToDTONonCyclic")
    InstructorDTO mapToDTONonCyclic(Instructor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    void update(InstructorDTO eventDTO, @MappingTarget Instructor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patch(InstructorPartialDTO partialDTO, @MappingTarget Instructor entity);
}
