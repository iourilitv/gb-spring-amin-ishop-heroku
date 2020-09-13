package ru.geekbrains.spring.ishop.rest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rest.outentities.*;
import ru.geekbrains.spring.ishop.service.EventService;
import ru.geekbrains.spring.ishop.utils.deserializers.OutEntityDeserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OutEntityService {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
            .create();
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public OutEntity createOutEntity(Object entity) {
        Map<String, Object> entityFields = new HashMap<>();
        OutEntity out = OutEntity.builder()
                .entityType(entity.getClass().getSimpleName())
                .entityFields(entityFields)
                .build();

        if(entity instanceof Event) {
            fillEventEntityFields((Event)entity, entityFields);
        } else if(entity instanceof ActionType) {
            fillActionTypeEntityFields((ActionType)entity, entityFields);
        } else if(entity instanceof Order) {
            fillOrderEntityFields((Order)entity, entityFields);
        } else if(entity instanceof OrderStatus) {
            fillOrderStatusEntityFields((OrderStatus)entity, entityFields);
        } else if(entity instanceof User) {
            fillUserEntityFields((User)entity, entityFields);
        } else if(entity instanceof OrderItem) {
            fillOrderItemEntityFields((OrderItem)entity, entityFields);
        } else if(entity instanceof Product) {
            fillProductEntityFields((Product)entity, entityFields);
        } else if(entity instanceof Category) {
            fillCategoryEntityFields((Category)entity, entityFields);
        } else if(entity instanceof Delivery) {
            fillDeliveryEntityFields((Delivery)entity, entityFields);
        } else if(entity instanceof Address) {
            fillAddressEntityFields((Address)entity, entityFields);
        }

        return out;
    }

    private void fillEventEntityFields(Event event, Map<String, Object> entityFields) {
        entityFields.put("id", event.getId());
        entityFields.put("actionType", createOutEntity(event.getActionType()));
        entityFields.put("issuer", event.getIssuer());
        entityFields.put("issuerEventId", event.getIssuerEventId());
        entityFields.put("entityType", event.getEntityType());
        entityFields.put("entityId", event.getEntityId());
        entityFields.put("entity", eventService.fillEntityFieldInEventOutEntity(event.getEntityType(), event.getEntityId()));
        entityFields.put("issuerCreatedAt", event.getIssuerCreatedAt());
        entityFields.put("recipientAcceptedAt", event.getRecipientAcceptedAt());
    }

    private void fillActionTypeEntityFields(ActionType actionType, Map<String, Object> entityFields) {
        entityFields.put("id", actionType.getId());
        entityFields.put("title", actionType.getTitle());
        entityFields.put("description", actionType.getDescription());
        entityFields.put("entityType", actionType.getEntityType());
    }

    private void fillOrderEntityFields(Order order, Map<String, Object> entityFields) {

        log.info("*********** fillOrderEntityFields ***********");

        entityFields.put("id", order.getId());
        entityFields.put("orderStatus", createOutEntity(order.getOrderStatus()));
        entityFields.put("user", createOutEntity(order.getUser()));

        List<OutEntity> orderItems = new ArrayList<>();
        order.getOrderItems().forEach(entity -> orderItems.add(createOutEntity(entity)));
        entityFields.put("orderItems", orderItems);

        log.info("orderItems: " + orderItems);

        entityFields.put("totalItemsCosts", order.getTotalItemsCosts());
        entityFields.put("totalCosts", order.getTotalCosts());
        entityFields.put("delivery", createOutEntity(order.getDelivery()));

        entityFields.put("createdAt", order.getCreatedAt());
        entityFields.put("updatedAt", order.getUpdatedAt());

        log.info("entityFields: " + entityFields);

    }

    private void fillOrderStatusEntityFields(OrderStatus orderStatus, Map<String, Object> entityFields) {
        entityFields.put("id", orderStatus.getId());
        entityFields.put("title", orderStatus.getTitle());
        entityFields.put("description", orderStatus.getDescription());
    }

    private void fillUserEntityFields(User user, Map<String, Object> entityFields) {
        entityFields.put("id", user.getId());
        entityFields.put("userName", user.getUserName());
        entityFields.put("firstName", user.getFirstName());
        entityFields.put("lastName", user.getLastName());
        entityFields.put("phoneNumber", user.getPhoneNumber());
        entityFields.put("email", user.getEmail());
        assert user.getDeliveryAddress() != null;
        entityFields.put("deliveryAddress", createOutEntity(user.getDeliveryAddress()));
    }

    private void fillOrderItemEntityFields(OrderItem orderItem, Map<String, Object> entityFields) {
        entityFields.put("id", orderItem.getId());
        entityFields.put("product", createOutEntity(orderItem.getProduct()));
        entityFields.put("itemPrice", orderItem.getItemPrice());
        entityFields.put("quantity", orderItem.getQuantity());
        entityFields.put("itemCosts", orderItem.getItemCosts());
        entityFields.put("order", orderItem.getOrder().getId());
    }

    private void fillProductEntityFields(Product product, Map<String, Object> entityFields) {
        entityFields.put("id", product.getId());
        entityFields.put("category", createOutEntity(product.getCategory()));
        entityFields.put("vendorCode", product.getVendorCode());
        entityFields.put("title", product.getTitle());
        entityFields.put("price", product.getPrice());
        entityFields.put("shortDescription", product.getShortDescription());
    }

    private void fillCategoryEntityFields(Category category, Map<String, Object> entityFields) {
        entityFields.put("id", category.getId());
        entityFields.put("title", category.getTitle());
    }

    private void fillDeliveryEntityFields(Delivery delivery, Map<String, Object> entityFields) {
        entityFields.put("id", delivery.getId());
        entityFields.put("order", delivery.getOrder().getId());
        entityFields.put("phoneNumber", delivery.getPhoneNumber());
        entityFields.put("deliveryAddress", createOutEntity(delivery.getDeliveryAddress()));
        entityFields.put("deliveryCost", delivery.getDeliveryCost());
        entityFields.put("deliveryExpectedAt", delivery.getDeliveryExpectedAt());
        entityFields.put("deliveredAt", delivery.getDeliveredAt());
    }

    private void fillAddressEntityFields(Address address, Map<String, Object> entityFields) {
        entityFields.put("id", address.getId());
        entityFields.put("country", address.getCountry());
        entityFields.put("city", address.getCity());
        entityFields.put("address", address.getAddress());
    }

    public Event recognizeAndSaveEventFromOutEntity(String outEntityJson) {
        OutEntity eventOutEntity = gson.fromJson(outEntityJson, OutEntity.class);
        Map<String, Object> eventFields = eventOutEntity.getEntityFields();
//        Event event = eventService.assembleEventFromFields(eventOutEntity.getEntityClassSimpleName(), eventFields);
        Event event = new Event();
//        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get("issuerEventId")));
//        double doubleId = Double.parseDouble(String.valueOf(fields.get("id")));
//        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get("entityId")));
//        OutEntity actionTypeOutEntity = (OutEntity)fields.get("actionType");
//
//        log.info("actionTypeOutEntity= " + actionTypeOutEntity);
//
//        String actionTypeTitle = String.valueOf(actionTypeOutEntity.getBody().get("title"));
//
//        log.info("actionTypeTitle= " + actionTypeTitle);
//
//        Event event = Event.builder()
//            .id((new Double(doubleId)).longValue())
////            .actionType(eventService.findActionTypeByTitle((String) fields.get("actionType")))
//            .actionType(eventService.findActionTypeByTitle(actionTypeTitle))
//            .issuer((String) fields.get("issuer"))
//            .issuerEventId((new Double(doubleIssuerEventId)).longValue())
//            .entityType((String) fields.get("entityType"))
//            .entityId((new Double(doubleEntityId)).longValue())
//            .issuerCreatedAt(LocalDateTime.parse(String.valueOf(fields.get("issuerCreatedAt"))))
//            .recipientAcceptedAt(LocalDateTime.now())
//            .build();
//        log.info("****** recognizeAndSaveEventFromOutEntity() event: " + event);

//        Event copyEvent = eventService.createAndSaveIncomingEvent(ActionTypes.STATUS_CHANGED.name(),
//                        Event.Issuers.SYSTEM.name(), event.getId());

        return event;
//        return eventService.save(copyEvent);
//        return eventService.save(event);
    }

//    public Event recognizeEventFromOutEntity(String outEntityJson) {
//        Map<String, Object> eventFields = gson.fromJson(outEntityJson, OutEntity.class).getBody();
//        double doubleIssuerEventId = Double.parseDouble(String.valueOf(eventFields.get("issuerEventId")));
//        double doubleId = Double.parseDouble(String.valueOf(eventFields.get("id")));
//        double doubleEntityId = Double.parseDouble(String.valueOf(eventFields.get("entityId")));
//
//        OutEntity actionTypeOutEntity = gson.fromJson((JsonElement) eventFields.get("actionType"), OutEntity.class);;
//
//        log.info("actionTypeOutEntity= " + actionTypeOutEntity);
//
//        String actionTypeTitle = String.valueOf(actionTypeOutEntity.getBody().get("title"));
//
//        log.info("actionTypeTitle= " + actionTypeTitle);
//
//        Event event = Event.builder()
//                .id((new Double(doubleId)).longValue())
//                .actionType(eventService.findActionTypeByTitle(actionTypeTitle))
//                .issuer((String) eventFields.get("issuer"))
//                .issuerEventId((new Double(doubleIssuerEventId)).longValue())
//                .entityType((String) eventFields.get("entityType"))
//                .entityId((new Double(doubleEntityId)).longValue())
//                .issuerCreatedAt(LocalDateTime.parse(String.valueOf(eventFields.get("issuerCreatedAt"))))
//                .recipientAcceptedAt(LocalDateTime.now())
//                .build();
//        log.info("****** recognizeAndSaveEventFromOutEntity() event: " + event);
//        return eventService.save(event);
//    }

}
