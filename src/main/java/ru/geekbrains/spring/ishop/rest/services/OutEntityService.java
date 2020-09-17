package ru.geekbrains.spring.ishop.rest.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rest.outentities.*;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutEntityService {
    private final OutEntityDeserializer outEntityDeserializer;

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
        entityFields.put(Event.Fields.id.name(), event.getId());
        entityFields.put(Event.Fields.actionType.name(), createOutEntity(event.getActionType()));
        entityFields.put(Event.Fields.issuer.name(), event.getIssuer());
        entityFields.put(Event.Fields.issuerEventId.name(), event.getIssuerEventId());
        entityFields.put(Event.Fields.entityType.name(), event.getEntityType());
        entityFields.put(Event.Fields.entityId.name(), event.getEntityId());
        entityFields.put(Event.Fields.issuerCreatedAt.name(), event.getIssuerCreatedAt());
        entityFields.put(Event.Fields.recipientAcceptedAt.name(), event.getRecipientAcceptedAt());
    }

    private void fillActionTypeEntityFields(ActionType actionType, Map<String, Object> entityFields) {
        entityFields.put(ActionType.Fields.id.name(), actionType.getId());
        entityFields.put(ActionType.Fields.title.name(), actionType.getTitle());
        entityFields.put(ActionType.Fields.description.name(), actionType.getDescription());
        entityFields.put(ActionType.Fields.entityType.name(), actionType.getEntityType());
    }

    private void fillOrderEntityFields(Order order, Map<String, Object> entityFields) {
        entityFields.put("id", order.getId());
        entityFields.put("orderStatus", createOutEntity(order.getOrderStatus()));
        entityFields.put("user", createOutEntity(order.getUser()));

        List<OutEntity> orderItems = new ArrayList<>();
        order.getOrderItems().forEach(entity -> orderItems.add(createOutEntity(entity)));
        entityFields.put("orderItems", orderItems);

        entityFields.put("totalItemsCosts", order.getTotalItemsCosts());
        entityFields.put("totalCosts", order.getTotalCosts());
        entityFields.put("delivery", createOutEntity(order.getDelivery()));
        entityFields.put("createdAt", order.getCreatedAt());
        entityFields.put("updatedAt", order.getUpdatedAt());
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
        if(user.getDeliveryAddress() != null) {
            entityFields.put("deliveryAddress", createOutEntity(user.getDeliveryAddress()));
        }
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

    //TODO ONLY for Testing
//    public Event recognizeAndSaveEventFromOutEntityJsonString(String outEntityJson) {
//        OutEntity outEntity = outEntityDeserializer.recognizeOutEntity(outEntityJson);
//        return eventBuilder.create(outEntity);
////        return eventService.save(event);
//    }
//    public Event recognizeAndSaveEventFromOutEntityJsonString(String outEntityJson) {
//        log.info("*** incoming outEntityJson: " + outEntityJson);
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Event.class, OutEntityDeserializer.class)
//                .create();
//        Event event = (Event) gson.fromJson(outEntityJson, Event.class);
////        Event event = (Event) outEntityDeserializer.recognizeEntity(outEntityJson);
//        return event;
////        return eventService.save(event);
//    }
    public Event recognizeAndSaveEventFromOutEntityJsonString(String outEntityJson) {
        Event event = (Event) outEntityDeserializer.recognizeEntity(outEntityJson);
        event.setRecipientAcceptedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        return event;
//        return eventService.save(event);
    }


}
