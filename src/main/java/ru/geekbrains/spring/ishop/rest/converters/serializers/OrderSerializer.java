package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderSerializer implements IEntitySerializer {
    private final OutEntitySerializer outEntitySerializer;

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Order order = (Order) entity;
        entityFields.put(Order.Fields.id.name(), order.getId());
        entityFields.put(Order.Fields.orderStatus.name(), outEntitySerializer.convertEntityToOutEntity(order.getOrderStatus()));
        entityFields.put(Order.Fields.user.name(), outEntitySerializer.convertEntityToOutEntity(order.getUser()));

        List<OutEntity> orderItems = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> orderItems.add(outEntitySerializer.convertEntityToOutEntity(orderItem)));
        entityFields.put(Order.Fields.orderItems.name(), orderItems);

        entityFields.put(Order.Fields.totalItemsCosts.name(), order.getTotalItemsCosts());
        entityFields.put(Order.Fields.totalCosts.name(), order.getTotalCosts());
        entityFields.put(Order.Fields.delivery.name(), outEntitySerializer.convertEntityToOutEntity(order.getDelivery()));
        entityFields.put(Order.Fields.createdAt.name(), order.getCreatedAt());
        entityFields.put(Order.Fields.updatedAt.name(), order.getUpdatedAt());
    }

}
