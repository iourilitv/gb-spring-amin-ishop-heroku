package ru.geekbrains.spring.ishop.informing.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.informing.MailService;
import ru.geekbrains.spring.ishop.informing.messages.AbstractMailMessage;
import ru.geekbrains.spring.ishop.informing.messages.OrderMessage;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;

@Service
public class MailObserver implements IObserver {
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void update(AbstractSubject subject, Object arg) {
        if(subject instanceof OrderSubject) {
            //TODO здесь можно добавить разделение на разные подтипы OrderMessage
            OrderMessage orderMessage = (OrderMessage) arg;
            AbstractMailMessage orderMailMessage =
                    mailService.createOrderMailMessage(
                            orderMessage.getOrder(), orderMessage.getTextTemplate());

            mailService.addMessageToQueue(orderMailMessage);
        }

        //if(subject instanceof AnotherSubject)
    }

}
