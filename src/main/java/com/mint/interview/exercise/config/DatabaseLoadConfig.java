package com.mint.interview.exercise.config;

import com.mint.interview.exercise.domain.Event;
import com.mint.interview.exercise.domain.EventType;
import com.mint.interview.exercise.domain.Instructor;
import com.mint.interview.exercise.repository.EventRepository;
import com.mint.interview.exercise.repository.EventTypeRepository;
import com.mint.interview.exercise.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class DatabaseLoadConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoadConfig.class);

    @Bean
    CommandLineRunner initDatabase(EventRepository eventRepository, EventTypeRepository eventTypeRepository,
                                   InstructorRepository instructorRepository) {
        PodamFactory factory = new PodamFactoryImpl();

        return args -> {
            log.info("Preloading EventTypes...");
            EventType weekOff = eventTypeRepository.save(new EventType("Week Off"));
            EventType seminar = eventTypeRepository.save(new EventType("Seminar"));
            EventType project = eventTypeRepository.save(new EventType("Project"));

            log.info("Preloading Instructors...");
            List<Instructor> instructors = new ArrayList<>();
            IntStream.range(0, 5).forEach(i -> {
                Instructor instructor = factory.manufacturePojo(Instructor.class);
                instructors.add(instructorRepository.save(instructor));
            });

            log.info("Preloading Events...");
            for (Instructor instructor: instructors) {
                LocalDateTime aux = LocalDateTime.now();

                Event weekOffEvent = factory.manufacturePojo(Event.class);
                weekOffEvent.setStartDate(aux);
                weekOffEvent.setEndDate(aux.plusWeeks(1));
                weekOffEvent.setType(weekOff);
                weekOffEvent.setInstructor(instructor);
                eventRepository.save(weekOffEvent);

                aux = aux.plusWeeks(1).plusDays(1);

                Event seminarEvent = factory.manufacturePojo(Event.class);
                seminarEvent.setStartDate(aux);
                seminarEvent.setEndDate(aux.plusWeeks(1));
                seminarEvent.setType(seminar);
                seminarEvent.setInstructor(instructor);
                eventRepository.save(seminarEvent);

                aux = aux.plusWeeks(1).plusDays(1);

                Event seminar2Event = factory.manufacturePojo(Event.class);
                seminar2Event.setStartDate(aux);
                seminar2Event.setEndDate(aux.plusWeeks(1));
                seminar2Event.setType(seminar);
                seminar2Event.setInstructor(instructor);
                eventRepository.save(seminar2Event);

                aux = aux.plusWeeks(1).plusDays(1);

                Event projectEvent = factory.manufacturePojo(Event.class);
                projectEvent.setStartDate(aux);
                projectEvent.setEndDate(aux.plusWeeks(1));
                projectEvent.setType(project);
                projectEvent.setInstructor(instructor);
                eventRepository.save(projectEvent);

            }

        };
    }

}
