package ru.geekbrains.spring.ishop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.EventRepository;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
//@RequiredArgsConstructor //TODO Learn why it does not work
public class EventService {
    private OutEntityService outEntityService;

    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

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

//    @Transactional
//    @PostConstruct //TODO Learn why it does not work
//    public void initInitialEvent() {
//        if(findByTitle("Initial event") !=null) {
//            return;
//        }
//        Event event = new Event();
//        event.setActionType("Initialized");
//        event.setTitle("Initial event");
//        event.setDescription("This store app has been initialized");
//        eventRepository.save(event);
//    }

    //TODO Delete for test only - for production use repository
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

    public void fillEntityFieldInEventOutEntity(String entityType, Long entityId, Map<String, Object> entityFields) {
        //TODO Refactoring DB: make entityType Nullable
        if(entityId == null) {
            return;
        }
        if(entityType.contains("Order")) {
            if(entityType.contains("OrderStatus")) {
                entityFields.put("OrderStatus", outEntityService.createOutEntity(orderService.findOrderStatusById(entityId)));
            } else if(entityType.contains("OrderItem")) {
                entityFields.put("OrderItem", outEntityService.createOutEntity(orderService.findOrderItemById(entityId)));
            }
            entityFields.put("Order", outEntityService.createOutEntity(orderService.findByIdOptional(entityId)));
        } else if(entityType.contains("User")) {
            entityFields.put("User", outEntityService.createOutEntity(userService.findByIdOptional(entityId)));
        } else if(entityType.contains("Product")) {
            entityFields.put("Product", outEntityService.createOutEntity(productService.findByIdOptional(entityId)));
        } else if(entityType.contains("Category")) {
            entityFields.put("Category", outEntityService.createOutEntity(categoryService.findById(Short.valueOf(String.valueOf(entityId)))));
        } else if(entityType.contains("Delivery")) {
            entityFields.put("Delivery", outEntityService.createOutEntity(deliveryService.findById(entityId)));
        } else if(entityType.contains("Address")) {
            entityFields.put("Address", outEntityService.createOutEntity(addressService.findById(entityId)));
        }
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
