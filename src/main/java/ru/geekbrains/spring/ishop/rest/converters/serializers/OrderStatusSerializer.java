package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.OrderStatus;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderStatusSerializer implements IEntitySerializer {
    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        OrderStatus orderStatus = (OrderStatus) entity;
        entityFields.put(OrderStatus.Fields.id.name(), orderStatus.getId());
        entityFields.put(OrderStatus.Fields.title.name(), orderStatus.getTitle());
        entityFields.put(OrderStatus.Fields.description.name(), orderStatus.getDescription());
    }

}
