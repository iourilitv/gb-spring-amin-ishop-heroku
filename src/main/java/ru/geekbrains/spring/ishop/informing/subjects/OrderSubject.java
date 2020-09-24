package ru.geekbrains.spring.ishop.informing.subjects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.informing.TextTemplates;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.observers.*;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class OrderSubject extends AbstractSubject {
    private final EventObserver eventObserver;

    private final MailObserver mailObserver;

    @PostConstruct
    void init() {
        attach(eventObserver);
        attach(mailObserver);
    }

    public void requestToSendMessage(Order order, TextTemplates operation) {
        notice(new OrderMessage(order, operation));
    }

}
