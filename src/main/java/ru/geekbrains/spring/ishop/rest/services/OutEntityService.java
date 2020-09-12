package ru.geekbrains.spring.ishop.rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rest.outentities.*;
import ru.geekbrains.spring.ishop.service.EventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OutEntityService {
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public OutEntity createOutEntity(Object entity) {
    OutEntity out = new OutEntity(entity.getClass().getSimpleName());
    Map<String, Object> entityFields = out.getBody();

        if(entity instanceof Event) {
            fillEventEntityFields((Event)entity, entityFields);
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

        log.info("*********** fillStoreEventEntityFields ***********");

        entityFields.put("id", event.getId());
        entityFields.put("actionType", event.getActionType());
        entityFields.put("title", event.getTitle());
        entityFields.put("description", event.getDescription());
        entityFields.put("entityType", event.getEntityType());
        entityFields.put("entityId", event.getEntityId());

        log.info("EntityType: " + event.getEntityType() + ". EntityId: " + event.getEntityId());

        eventService.fillEntityFieldInEventOutEntity(event.getEntityType(), event.getEntityId(), entityFields);
        entityFields.put("createdAt", event.getCreatedAt());
        entityFields.put("serverAcceptedAt", event.getServerAcceptedAt());

        log.info("entityFields: " + entityFields);

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

    public Event recognizeAndSaveEventFromOutEntity(OutEntity outEntity) {
        Event event = new Event();
        Map<String, Object> fields = outEntity.getBody();

        double doubleId = Double.parseDouble(String.valueOf(fields.get("id")));
        event.setId((new Double(doubleId)).longValue());

        event.setActionType("Incoming");
        event.setTitle((String) fields.get("title"));
        event.setDescription((String) fields.get("description"));
        event.setEntityType((String) fields.get("entityType"));

        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get("entityId")));
        event.setEntityId((new Double(doubleEntityId)).longValue());

        event.setCreatedAt(LocalDateTime.parse(String.valueOf(fields.get("createdAt"))));
        event.setServerAcceptedAt(LocalDateTime.now());

        Event copyEvent = eventService.createEvent("Event", "Incoming", event.getId());

//        return event;
        return eventService.save(copyEvent);
    }


}
