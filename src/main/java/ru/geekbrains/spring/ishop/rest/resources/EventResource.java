package ru.geekbrains.spring.ishop.rest.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/event")
@Slf4j
public class EventResource extends AbstractResource {

    private OutEntityService outEntityService;

    private EventService eventService;

    @Autowired
    public void setEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/{eventId}/eventId")
    public ResponseEntity<OutEntity> getEventOutEntity(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.createOutEntity(eventService.findById(eventId)));
    }

    @GetMapping(value = "/serverUnaccepted/first")
    public ResponseEntity<OutEntity> getFirstServerUnacceptedEventOutEntity() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.createOutEntity(eventService.findFirstByServerAcceptedAtIsNull()));
    }

    @PutMapping(value = "/{eventId}/eventId/serverAcceptedAt")
    public ResponseEntity<OutEntity> updateServerAcceptedAtFieldOfEventOutEntity(
            @RequestBody @Valid LocalDateTime serverAcceptedAt,
            @PathVariable("eventId") Long eventId) {
        Event oldEvent = eventService.findById(eventId);
        oldEvent.setServerAcceptedAt(serverAcceptedAt);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(outEntityService.createOutEntity(eventService.save(oldEvent)));
    }

}
