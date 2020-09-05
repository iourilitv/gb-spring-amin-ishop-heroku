package ru.geekbrains.spring.ishop.informing.observers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;
import ru.geekbrains.spring.ishop.service.EventService;

@Service
@Slf4j
public class EventObserver implements IObserver {
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void update(AbstractSubject subject, Object arg) {
        if(subject instanceof OrderSubject) {
            //TODO здесь можно добавить разделение на разные подтипы OrderMessage
            OrderMessage orderMessage = (OrderMessage) arg;
            Event event = eventService.createEvent("Order",
                    orderMessage.getTextTemplate().name(), orderMessage.getOrder().getId());
        }
        //if(subject instanceof AnotherSubject)
    }

}
