package ru.geekbrains.spring.ishop.service;

import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.ActionTypeRepository;
import ru.geekbrains.spring.ishop.repository.EventRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private OutEntityService outEntityService;

    //TODO does not work with @ RequiredArgsConstructor
    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final DeliveryService deliveryService;
    private final AddressService addressService;
    private final EventRepository eventRepository;
    private final ActionTypeRepository actionTypeRepository;

//    public Event createEvent(String entityType, String actionType, Long entityId) {
//        Event event = Event.builder()
//            .actionType(actionType)
//            .title(entityType + " " + actionType)
//            .description(entityType + " has been " + actionType)
//            .entityType(entityType)
//            .entityId(entityId)
//            .createdAt(LocalDateTime.now())
//            .build();
//        return save(event);
//    }
    public Event createAndSaveEvent(String actionTypeTitle, String entityType, Long entityId) {
        ActionType actionType = actionTypeRepository.getActionTypeByTitleEquals(actionTypeTitle)
                .orElseThrow(() -> new NotFoundException("The ActionType with title=" + actionTypeTitle + " is not found!"));
        Event event = Event.builder()
                .actionType(actionType)
                .issuer(Event.Issuers.STORE.name())
                .issuerEventId(0L)
                .entityType(entityType)
                .entityId(entityId)
                .issuerCreatedAt(LocalDateTime.now())
                .build();
        return save(event);
    }

    public Event createAndSaveIncomingEvent(String actionTypeTitle, String issuer, Long issuerEventId,
                                            String entityType, Long entityId, LocalDateTime issuerCreatedAt) {
        Event event = Event.builder()
                .actionType(findActionTypeByTitle(actionTypeTitle))
                .issuer(issuer)
                .issuerEventId(issuerEventId)
                .entityType(entityType)
                .entityId(entityId)
                .issuerCreatedAt(issuerCreatedAt)
                .build();
        return save(event);
    }

//    public Event assembleEventFromFields(String entityClassSimpleName, Map<String, Object> fields) {
//        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get("issuerEventId")));
//        double doubleId = Double.parseDouble(String.valueOf(fields.get("id")));
//        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get("entityId")));
//        OutEntity actionTypeOutEntity = gson.fromJson((JsonElement) eventFields.get("actionType"), OutEntity.class);;
//
//        return createAndSaveIncomingEvent(String actionTypeTitle, String issuer, Long issuerEventId,
//                String entityType, Long entityId, LocalDateTime issuerCreatedAt);
//    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " is not found!"));
    }

//    private Event findByTitle(String title) {
//        return eventRepository.findByTitle(title);
//    }

//    public Event findFirstByServerAcceptedAtIsNull() {
//        return eventRepository.findFirstByServerAcceptedAtIsNull().orElse(Event.nullObject);
//    }
    public Event findFirstByServerAcceptedAtIsNull() {
        return eventRepository.findFirstByRecipientAcceptedAtIsNullAndIssuerEquals(Event.Issuers.STORE.name())
                .orElse(Event.nullObject);
    }

//    private List<Event> findAll() {
//        return eventRepository.findAll();
//    }

    public ActionType findActionTypeByTitle(String actionTypeTitle) {
        return actionTypeRepository.getActionTypeByTitleEquals(actionTypeTitle)
                .orElseThrow(() -> new NotFoundException("The ActionType with title=" + actionTypeTitle + " is not found!"));
    }

//    public void fillEntityFieldInEventOutEntity(String entityType, Long entityId, Map<String, Object> entityFields) {
//        //TODO Refactoring DB: make entityType Nullable
//        if(entityId == null) {
//            return;
//        }
//        if(entityType.contains("Order")) {
//            if(entityType.contains("OrderStatus")) {
//                entityFields.put("OrderStatus", outEntityService.createOutEntity(orderService.findOrderStatusById(entityId)));
//            } else if(entityType.contains("OrderItem")) {
//                entityFields.put("OrderItem", outEntityService.createOutEntity(orderService.findOrderItemById(entityId)));
//            }
//            entityFields.put("Order", outEntityService.createOutEntity(orderService.findByIdOptional(entityId)));
//        } else if(entityType.contains("User")) {
//            entityFields.put("User", outEntityService.createOutEntity(userService.findByIdOptional(entityId)));
//        } else if(entityType.contains("Product")) {
//            entityFields.put("Product", outEntityService.createOutEntity(productService.findByIdOptional(entityId)));
//        } else if(entityType.contains("Category")) {
//            entityFields.put("Category", outEntityService.createOutEntity(categoryService.findById(Short.valueOf(String.valueOf(entityId)))));
//        } else if(entityType.contains("Delivery")) {
//            entityFields.put("Delivery", outEntityService.createOutEntity(deliveryService.findById(entityId)));
//        } else if(entityType.contains("Address")) {
//            entityFields.put("Address", outEntityService.createOutEntity(addressService.findById(entityId)));
//        }
//    }
//    public void fillEntityFieldInEventOutEntity(String entityType, Long entityId, Map<String, Object> entityFields) {
//        //TODO Refactoring DB: make entityType Nullable
//        if(entityId == null) {
//            return;
//        }
//        if(entityType.contains(EntityTypes.Order.name())) {
//            if(entityType.contains(EntityTypes.OrderStatus.name())) {
//                entityFields.put("entity", outEntityService.createOutEntity(orderService.findOrderStatusById(entityId)));
//            } else if(entityType.contains(EntityTypes.OrderItem.name())) {
//                entityFields.put("entity", outEntityService.createOutEntity(orderService.findOrderItemById(entityId)));
//            }
//            entityFields.put("entity", outEntityService.createOutEntity(orderService.findByIdOptional(entityId)));
//        } else if(entityType.contains(EntityTypes.User.name())) {
//            entityFields.put("entity", outEntityService.createOutEntity(userService.findByIdOptional(entityId)));
//        } else if(entityType.contains(EntityTypes.Product.name())) {
//            entityFields.put("entity", outEntityService.createOutEntity(productService.findByIdOptional(entityId)));
//        } else if(entityType.contains(EntityTypes.Category.name())) {
//            entityFields.put("entity", outEntityService.createOutEntity(categoryService.findById(Short.valueOf(String.valueOf(entityId)))));
//        } else if(entityType.contains(EntityTypes.Delivery.name())) {
//            entityFields.put("entity", outEntityService.createOutEntity(deliveryService.findById(entityId)));
//        } else if(entityType.contains(EntityTypes.Address.name())) {
//            entityFields.put("entity", outEntityService.createOutEntity(addressService.findById(entityId)));
//        }
//    }
    public Object fillEntityFieldInEventOutEntity(String entityType, Long entityId) {
        //TODO Refactoring DB: make entityType Nullable
        if(entityId == null) {
            return new Object();
        }
        if(entityType.contains(EntityTypes.Order.name())) {
            if(entityType.contains(EntityTypes.OrderStatus.name())) {
                return outEntityService.createOutEntity(orderService.findOrderStatusById(entityId));
            } else if(entityType.contains(EntityTypes.OrderItem.name())) {
                return outEntityService.createOutEntity(orderService.findOrderItemById(entityId));
            }
            return outEntityService.createOutEntity(orderService.findByIdOptional(entityId));
        } else if(entityType.contains(EntityTypes.User.name())) {
            return outEntityService.createOutEntity(userService.findByIdOptional(entityId));
        } else if(entityType.contains(EntityTypes.Product.name())) {
            return outEntityService.createOutEntity(productService.findByIdOptional(entityId));
        } else if(entityType.contains(EntityTypes.Category.name())) {
            return outEntityService.createOutEntity(categoryService.findById(Short.valueOf(String.valueOf(entityId))));
        } else if(entityType.contains(EntityTypes.Delivery.name())) {
            return outEntityService.createOutEntity(deliveryService.findById(entityId));
        } else if(entityType.contains(EntityTypes.Address.name())) {
            return outEntityService.createOutEntity(addressService.findById(entityId));
        }
        return new Object();
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
