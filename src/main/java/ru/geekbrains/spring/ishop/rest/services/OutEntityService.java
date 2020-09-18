package ru.geekbrains.spring.ishop.rest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFabric;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.ActionTypeDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.EventDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.outentities.*;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class OutEntityService {
    private final OutEntityDeserializer outEntityDeserializer;
    private final EventDeserializer eventDeserializer;
    private final ActionTypeDeserializer actionTypeDeserializer;
    private final DeserializerFabric deserializerFabric;

    public OutEntity convertEntityToOutEntity(Object entity) {
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
        entityFields.put(Event.Fields.actionType.name(), convertEntityToOutEntity(event.getActionType()));
        entityFields.put(Event.Fields.entityType.name(), event.getEntityType());
        entityFields.put(Event.Fields.entityId.name(), event.getEntityId());
        entityFields.put(Event.Fields.createdAt.name(), event.getCreatedAt());
        entityFields.put(Event.Fields.serverAcceptedAt.name(), event.getServerAcceptedAt());
    }

    private void fillActionTypeEntityFields(ActionType actionType, Map<String, Object> entityFields) {
        entityFields.put(ActionType.Fields.id.name(), actionType.getId());
        entityFields.put(ActionType.Fields.title.name(), actionType.getTitle());
        entityFields.put(ActionType.Fields.description.name(), actionType.getDescription());
        entityFields.put(ActionType.Fields.entityType.name(), actionType.getEntityType());
    }

    private void fillOrderEntityFields(Order order, Map<String, Object> entityFields) {
        entityFields.put("id", order.getId());
        entityFields.put("orderStatus", convertEntityToOutEntity(order.getOrderStatus()));
        entityFields.put("user", convertEntityToOutEntity(order.getUser()));

        List<OutEntity> orderItems = new ArrayList<>();
        order.getOrderItems().forEach(entity -> orderItems.add(convertEntityToOutEntity(entity)));
        entityFields.put("orderItems", orderItems);

        entityFields.put("totalItemsCosts", order.getTotalItemsCosts());
        entityFields.put("totalCosts", order.getTotalCosts());
        entityFields.put("delivery", convertEntityToOutEntity(order.getDelivery()));
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
            entityFields.put("deliveryAddress", convertEntityToOutEntity(user.getDeliveryAddress()));
        }
    }

    private void fillOrderItemEntityFields(OrderItem orderItem, Map<String, Object> entityFields) {
        entityFields.put("id", orderItem.getId());
        entityFields.put("product", convertEntityToOutEntity(orderItem.getProduct()));
        entityFields.put("itemPrice", orderItem.getItemPrice());
        entityFields.put("quantity", orderItem.getQuantity());
        entityFields.put("itemCosts", orderItem.getItemCosts());
        entityFields.put("order", orderItem.getOrder().getId());
    }

    private void fillProductEntityFields(Product product, Map<String, Object> entityFields) {
        entityFields.put("id", product.getId());
        entityFields.put("category", convertEntityToOutEntity(product.getCategory()));
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
        entityFields.put("deliveryAddress", convertEntityToOutEntity(delivery.getDeliveryAddress()));
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

    //TODO For Studding and Testing only
    public AbstractEntity recognizeEntityFromOutEntityJsonString(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, outEntityDeserializer)
                .create();
        OutEntity outEntity = gson.fromJson(jsonString, OutEntity.class);
        JsonElement jsonElement = (JsonElement) outEntity.getEntityFields().get(OutEntity.Fields.entityFields.name());
        AbstractEntity entity = outEntityDeserializer
                .deserializeEntityFromOutEntityJson(outEntity.getEntityType(), jsonElement);
        return Optional.ofNullable(entity).orElseThrow(() ->
                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
    }

    @PostConstruct
    private void initDeserializers() {
        Map<String, IEntityDeserializer> deserializers = new HashMap<>();
        deserializers.put(EntityTypes.Event.name(), this.eventDeserializer);
        deserializers.put(EntityTypes.ActionType.name(), this.actionTypeDeserializer);
        deserializerFabric.initDeserializerFabric(deserializers);
        outEntityDeserializer.setDeserializerFabric(deserializerFabric);
    }

}
