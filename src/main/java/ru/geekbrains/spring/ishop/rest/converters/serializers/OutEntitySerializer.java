package ru.geekbrains.spring.ishop.rest.converters.serializers;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFabric;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.util.*;

@Service
public class OutEntitySerializer {
    private SerializerFabric serializerFabric;

    public void setSerializerFabric(SerializerFabric serializerFabric) {
        this.serializerFabric = serializerFabric;
    }

    public OutEntity convertEntityToOutEntity(AbstractEntity entity) {
        Map<String, Object> entityFields = new HashMap<>();
        String entityType = entity.getClass().getSimpleName();
        OutEntity out = OutEntity.builder()
                .entityType(entityType)
                .entityFields(entityFields)
                .build();

        EntityTypes[] entityTypes = EntityTypes.values();
        for (EntityTypes type : entityTypes) {
            if (entityType.equals(type.name())) {
                serializerFabric.getSerializer(entityType).fillEntityFields(entity, entityFields);
            }
        }

//        if(entity instanceof Event) {
////            eventSerializer.fillEntityFields((Event)entity, entityFields);
//        } else if(entity instanceof ActionType) {
////            actionTypeSerializer.fillEntityFields((ActionType)entity, entityFields);
//        } else if(entity instanceof Order) {
//            fillOrderEntityFields((Order)entity, entityFields);
//        } else if(entity instanceof OrderStatus) {
//            fillOrderStatusEntityFields((OrderStatus)entity, entityFields);
//        } else if(entity instanceof User) {
//            fillUserEntityFields((User)entity, entityFields);
//        } else if(entity instanceof OrderItem) {
//            fillOrderItemEntityFields((OrderItem)entity, entityFields);
//        } else if(entity instanceof Product) {
//            fillProductEntityFields((Product)entity, entityFields);
//        } else if(entity instanceof Category) {
//            fillCategoryEntityFields((Category)entity, entityFields);
//        } else if(entity instanceof Delivery) {
//            fillDeliveryEntityFields((Delivery)entity, entityFields);
//        } else if(entity instanceof Address) {
//            fillAddressEntityFields((Address)entity, entityFields);
//        }
        return out;
    }

    private void fillOrderEntityFields(Order order, Map<String, Object> entityFields) {
//        entityFields.put("id", order.getId());
//        entityFields.put("orderStatus", convertEntityToOutEntity(order.getOrderStatus()));
//        entityFields.put("user", convertEntityToOutEntity(order.getUser()));
//
//        List<OutEntity> orderItems = new ArrayList<>();
//        order.getOrderItems().forEach(entity -> orderItems.add(convertEntityToOutEntity(entity)));
//        entityFields.put("orderItems", orderItems);
//
//        entityFields.put("totalItemsCosts", order.getTotalItemsCosts());
//        entityFields.put("totalCosts", order.getTotalCosts());
//        entityFields.put("delivery", convertEntityToOutEntity(order.getDelivery()));
//        entityFields.put("createdAt", order.getCreatedAt());
//        entityFields.put("updatedAt", order.getUpdatedAt());
    }

    private void fillOrderStatusEntityFields(OrderStatus orderStatus, Map<String, Object> entityFields) {
        entityFields.put("id", orderStatus.getId());
        entityFields.put("title", orderStatus.getTitle());
        entityFields.put("description", orderStatus.getDescription());
    }

    private void fillUserEntityFields(User user, Map<String, Object> entityFields) {
//        entityFields.put("id", user.getId());
//        entityFields.put("userName", user.getUserName());
//        entityFields.put("firstName", user.getFirstName());
//        entityFields.put("lastName", user.getLastName());
//        entityFields.put("phoneNumber", user.getPhoneNumber());
//        entityFields.put("email", user.getEmail());
//        if(user.getDeliveryAddress() != null) {
//            entityFields.put("deliveryAddress", convertEntityToOutEntity(user.getDeliveryAddress()));
//        }
    }

    private void fillOrderItemEntityFields(OrderItem orderItem, Map<String, Object> entityFields) {
//        entityFields.put("id", orderItem.getId());
//        entityFields.put("product", convertEntityToOutEntity(orderItem.getProduct()));
//        entityFields.put("itemPrice", orderItem.getItemPrice());
//        entityFields.put("quantity", orderItem.getQuantity());
//        entityFields.put("itemCosts", orderItem.getItemCosts());
//        entityFields.put("order", orderItem.getOrder().getId());
    }

    private void fillProductEntityFields(Product product, Map<String, Object> entityFields) {
//        entityFields.put("id", product.getId());
//        entityFields.put("category", convertEntityToOutEntity(product.getCategory()));
//        entityFields.put("vendorCode", product.getVendorCode());
//        entityFields.put("title", product.getTitle());
//        entityFields.put("price", product.getPrice());
//        entityFields.put("shortDescription", product.getShortDescription());
    }

    private void fillCategoryEntityFields(Category category, Map<String, Object> entityFields) {
        entityFields.put("id", category.getId());
        entityFields.put("title", category.getTitle());
    }

    private void fillDeliveryEntityFields(Delivery delivery, Map<String, Object> entityFields) {
//        entityFields.put("id", delivery.getId());
//        entityFields.put("order", delivery.getOrder().getId());
//        entityFields.put("phoneNumber", delivery.getPhoneNumber());
//        entityFields.put("deliveryAddress", convertEntityToOutEntity(delivery.getDeliveryAddress()));
//        entityFields.put("deliveryCost", delivery.getDeliveryCost());
//        entityFields.put("deliveryExpectedAt", delivery.getDeliveryExpectedAt());
//        entityFields.put("deliveredAt", delivery.getDeliveredAt());
    }

    private void fillAddressEntityFields(Address address, Map<String, Object> entityFields) {
        entityFields.put("id", address.getId());
        entityFields.put("country", address.getCountry());
        entityFields.put("city", address.getCity());
        entityFields.put("address", address.getAddress());
    }

}
