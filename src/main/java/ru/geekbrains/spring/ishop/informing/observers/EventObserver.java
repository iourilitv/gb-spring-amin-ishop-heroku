package ru.geekbrains.spring.ishop.informing.observers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.informing.TextTemplates;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;
import ru.geekbrains.spring.ishop.service.EventService;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventObserver implements IObserver {
    private final EventService eventService;

    @Override
    public void update(AbstractSubject subject, Object arg) {
        String actionTypeTitle = "";
        OrderMessage orderMessage = (OrderMessage) arg;
        if(subject instanceof OrderSubject) {

            if(orderMessage.getTextTemplate().equals(TextTemplates.NEW_ORDER_CREATED)) {
                actionTypeTitle = ActionType.Titles.CREATED.name();
            } else if(orderMessage.getTextTemplate().equals(TextTemplates.ORDER_STATUS_CHANGED)) {
                actionTypeTitle = ActionType.Titles.STATUS_CHANGED.name();
            }
            //TODO здесь можно добавить разделение на разные подтипы OrderMessage

            Event event = eventService.createAndSaveEvent(actionTypeTitle,
                    EntityTypes.Order.name(), orderMessage.getOrder().getId());
        }
        //if(subject instanceof AnotherSubject)
    }

}
