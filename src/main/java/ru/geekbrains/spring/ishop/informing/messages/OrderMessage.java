package ru.geekbrains.spring.ishop.informing.messages;

import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.informing.TextTemplates;

public class OrderMessage extends AbstractMessage {

    public OrderMessage() {
    }

    public OrderMessage(Order order, TextTemplates text) {
        super.origin = order;
        super.textTemplate = text;
    }

    public Order getOrder() {
        return (Order) super.origin;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "orderId=" + ((Order)origin).getId() +
                ", textTemplate=" + textTemplate +
                '}';
    }
}
