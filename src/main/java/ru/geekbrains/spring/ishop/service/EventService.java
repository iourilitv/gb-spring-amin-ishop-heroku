package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.ActionTypeRepository;
import ru.geekbrains.spring.ishop.repository.EventRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final OutEntityService outEntityService;
    private final EventRepository eventRepository;
    private final ActionTypeRepository actionTypeRepository;

    public Event createAndSaveEvent(String actionTypeTitle, String entityType, Long entityId) {
        ActionType actionType = actionTypeRepository.getActionTypeByTitleEquals(actionTypeTitle)
                .orElseThrow(() -> new NotFoundException("The ActionType with title: " + actionTypeTitle + " is not found!"));
        Event event = Event.builder()
                .actionType(actionType)
                .entityType(entityType)
                .entityId(entityId)
                .createdAt(LocalDateTime.now())
                .build();
        return Optional.ofNullable(save(event)).orElseThrow(() ->
                new NotFoundException("The Event with id: " + entityId + " has not been saved correctly!"));
    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " is not found!"));
    }

    public Event findFirstByServerAcceptedAtIsNull() {
        return eventRepository.findFirstByServerAcceptedAtIsNull()
                .orElse(Event.emptyObject);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event updateServerAcceptedAtFieldOfEvent(Long eventId, String newValue) {
        Event event = findById(eventId);
        event.setServerAcceptedAt(LocalDateTime.parse(newValue));
        return save(event);
    }

    public OutEntity convertEventToOutEntity(Event event) {
        return outEntityService.convertEntityToOutEntity(event);
    }

    //TODO For Studding and Testing only
    public Event recognizeAndSaveEventFromOutEntityJsonString(String outEntityJson) {
        Event event = (Event) outEntityService.recognizeEntityFromOutEntityJsonString(outEntityJson);
        event.setServerAcceptedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        return event;
    }

}
