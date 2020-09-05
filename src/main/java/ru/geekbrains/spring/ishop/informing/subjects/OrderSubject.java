package ru.geekbrains.spring.ishop.informing.subjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.informing.TextTemplates;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.observers.*;

import javax.annotation.PostConstruct;

@Component
public class OrderSubject extends AbstractSubject {
    private final EventObserver eventObserver;

    private final MailObserver mailObserver;
    private final FacebookObserver facebookObserver;
    private final WhatsAppObserver whatsAppObserver;
    private final SkypeObserver skypeObserver;

    @Autowired
    public OrderSubject(EventObserver eventObserver, MailObserver mailObserver, FacebookObserver facebookObserver, WhatsAppObserver whatsAppObserver, SkypeObserver skypeObserver) {
        this.eventObserver = eventObserver;
        this.mailObserver = mailObserver;
        this.facebookObserver = facebookObserver;
        this.whatsAppObserver = whatsAppObserver;
        this.skypeObserver = skypeObserver;
    }

    @PostConstruct
    void init() {
        attach(eventObserver);

        attach(mailObserver);
        attach(facebookObserver);
        attach(whatsAppObserver);
        attach(skypeObserver);
    }

    public void requestToSendMessage(Order order, TextTemplates operation) {
        notice(new OrderMessage(order, operation));
    }

}
