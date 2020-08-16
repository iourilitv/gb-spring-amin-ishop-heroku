package ru.geekbrains.spring.ishop.informing.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;

@Service
public class SkypeObserver implements IObserver {
    Logger logger = LoggerFactory.getLogger(SkypeObserver.class);

    @Override
    public void update(AbstractSubject subject, Object arg) {
        if(subject instanceof OrderSubject) {
            //TODO здесь можно добавить разделение на разные подтипы OrderMessage
            OrderMessage orderMessage = (OrderMessage) arg;
            logger.info("******** update() *********\n" +
                    "Has sent through Skype: " + orderMessage);

        }
    }
}
