package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService2;
import ru.geekbrains.spring.ishop.service.EventService;

@RestController
@RequestMapping("/api/v1/event")
public class EventResource extends AbstractResource {

    private OutEntityService2 outEntityService;

    private EventService eventService;

    @Autowired
    public void setEntityService(OutEntityService2 outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Без него по умолчанию вернет код ошибки 500 - ошибка на сервере
     * @param id - event id
     * @return - json объект OutEntity с запрашиваемым объектом.
     *      Если нет запрашиваемого объекта, то вернется объект исключения NotFoundException "404, "The {Entity} with id=99 is not found!""
     */
    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutEntity findById(@PathVariable("id") long id) {
        return outEntityService.createOutEntity(eventService.findByIdOptional(id));
    }

}
