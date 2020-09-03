package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.EventRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
//@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final DeliveryService deliveryService;
    private final AddressService addressService;

    @Autowired
    public EventService(EventRepository eventRepository, OrderService orderService, UserService userService, ProductService productService, CategoryService categoryService, DeliveryService deliveryService, AddressService addressService) {
        this.eventRepository = eventRepository;
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.deliveryService = deliveryService;
        this.addressService = addressService;
    }

    //    @Autowired
//    public void setEventRepository(EventRepository eventRepository) {
//        this.eventRepository = eventRepository;
//    }
//
//    //TODO for test only
//    @Autowired
//    public void setOrderService(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    @Transactional
    @PostConstruct
    public void initInitialEvent() {
        if(findByTitle("Initial event") !=null) {
            return;
        }
        Event event = new Event();
        event.setActionType("Initialized");
        event.setTitle("Initial event");
        event.setDescription("This store app has been initialized");
        eventRepository.save(event);
    }

    //TODO for test only - for production use repository
    public Event createEvent(String entityType, String actionType, Long entityId) {
        Event event = new Event();
        event.setEntityType(entityType);
        event.setActionType(actionType);
        event.setTitle(event.getEntityType() + " " + event.getActionType());
        event.setDescription(entityType + " has been " + actionType);
        event.setEntityId(entityId);
        event.setCreatedAt(LocalDateTime.now());
        eventRepository.save(event);
        return event;
    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event with id=" + id + " is not found!"));
    }

    private Event findByTitle(String title) {
        return eventRepository.findByTitle(title);
    }

    public Event findFirstByServerAcceptedAtIsNull() {
        return eventRepository.findFirstByServerAcceptedAtIsNull().orElse(new Event());
    }

    private List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void fillEntityField(String entityType, Long entityId, Map<String, Object> entityFields) {
        if(entityType.contains("Event")) {
            entityFields.put("Event", findById(entityId));
        } else if(entityType.contains("Order")) {
            if(entityType.contains("OrderStatus")) {
                entityFields.put("OrderStatus", orderService.findOrderStatusById(entityId));
            } else if(entityType.contains("OrderItem")) {
                entityFields.put("OrderItem", orderService.findOrderItemById(entityId));
            }
            entityFields.put("Order", orderService.findByIdOptional(entityId));
        } else if(entityType.contains("User")) {
            entityFields.put("User", userService.findByIdOptional(entityId));
        } else if(entityType.contains("Product")) {
            entityFields.put("Product", productService.findByIdOptional(entityId));
        } else if(entityType.contains("Category")) {
            entityFields.put("Category", categoryService.findById(Short.valueOf(String.valueOf(entityId))));
        } else if(entityType.contains("Delivery")) {
            entityFields.put("Delivery", deliveryService.findById(entityId));
        } else if(entityType.contains("Address")) {
            entityFields.put("Address", addressService.findById(entityId));
        }
    }

}
