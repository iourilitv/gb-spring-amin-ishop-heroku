package ru.geekbrains.spring.ishop.rest.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.service.EventService;

@RestController
@RequestMapping("/api/v1/event")
@Slf4j
@RequiredArgsConstructor
public class EventResource extends AbstractResource {
    private final EventService eventService;

    @GetMapping(value = "/{eventId}/eventId")
    public ResponseEntity<OutEntity> getEventOutEntity(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(eventService.convertEventToOutEntity(eventService.findById(eventId)));
    }

    @GetMapping(value = "/serverUnaccepted/first")
    public ResponseEntity<OutEntity> getFirstServerUnacceptedEventOutEntity() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(eventService.convertEventToOutEntity(eventService.findFirstByServerAcceptedAtIsNull()));
    }

    @PutMapping(value = "/{eventId}/eventId/serverAcceptedAt/string")
    public ResponseEntity<OutEntity> updateServerAcceptedAtFieldOfEvent(
            @RequestBody @Valid String serverAcceptedAt,
            @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(eventService.convertEventToOutEntity(
                        eventService.updateServerAcceptedAtFieldOfEvent(eventId, serverAcceptedAt)));
    }

    //TODO For Studding and Testing only
    @PostMapping(value = "/save/incoming/string")
    public ResponseEntity<OutEntity> saveIncomingStringEventOutEntity(@RequestBody @Valid String json) {
        Event event = eventService.recognizeAndSaveEventFromOutEntityJsonString(json);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(eventService.convertEventToOutEntity(event));
    }

}
