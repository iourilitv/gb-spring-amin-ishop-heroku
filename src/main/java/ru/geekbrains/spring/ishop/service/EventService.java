package ru.geekbrains.spring.ishop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Event;

import java.time.LocalDateTime;

@Service
public class EventService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    //TODO for test only
    private OrderService orderService;

    //TODO for test only
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    //TODO for test only - for production use repository
    public Event findByIdOptional(Long id) {
        Event event = new Event();
        event.setId(id);
        event.setType("order");
        event.setTitle("OrderCreated");
        event.setDescription("Customer has created a new order");

        //TODO for test only
        event.setEntity(orderService.findById(id));

//        event.setDeliveryStatus();

        event.setCreatedAt(LocalDateTime.now());

//        event.setDeliveredAt();

        return event;
    }

}
