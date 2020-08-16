package ru.geekbrains.spring.ishop.informing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.geekbrains.spring.ishop.entity.Order;

@Service
public class MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildOrderEmail(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        String notice;
        if(order.getOrderStatus().getTitle().equalsIgnoreCase("Created")) {
            notice = String.format(TextTemplates.NOTICE_NEW_ORDER_CREATED.getText(),
                    order.getId());
        } else {
            notice = String.format(TextTemplates.NOTICE_ORDER_STATUS_CHANGED.getText(),
                    order.getId(), order.getOrderStatus().getTitle());
        }
        context.setVariable("notice", notice);

        return templateEngine.process("amin/messages/order-mail", context);
    }

}
