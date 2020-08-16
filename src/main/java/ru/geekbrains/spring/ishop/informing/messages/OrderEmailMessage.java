package ru.geekbrains.spring.ishop.informing.messages;

import ru.geekbrains.spring.ishop.entity.Order;

public class OrderEmailMessage extends AbstractMailMessage {
    public Order getOrder() {
        return (Order) super.getOrigin();
    }

}
