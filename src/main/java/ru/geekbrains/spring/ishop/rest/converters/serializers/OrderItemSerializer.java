package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.OrderItem;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderItemSerializer implements IEntitySerializer {
    private final OutEntitySerializer outEntitySerializer;

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        OrderItem orderItem = (OrderItem) entity;
        entityFields.put(OrderItem.Fields.id.name(), orderItem.getId());
        entityFields.put(OrderItem.Fields.product.name(), outEntitySerializer.convertEntityToOutEntity(orderItem.getProduct()));
        entityFields.put(OrderItem.Fields.itemPrice.name(), orderItem.getItemPrice());
        entityFields.put(OrderItem.Fields.quantity.name(), orderItem.getQuantity());
        entityFields.put(OrderItem.Fields.itemCosts.name(), orderItem.getItemCosts());
        entityFields.put(OrderItem.Fields.order.name(), orderItem.getOrder().getId());
    }

}
