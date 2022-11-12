package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliverySerializer implements IEntitySerializer {
    private final OutEntitySerializer outEntitySerializer;

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Delivery delivery = (Delivery) entity;
        entityFields.put(Delivery.Fields.id.name(), delivery.getId());
        entityFields.put(Delivery.Fields.order.name(), delivery.getOrder().getId());
        entityFields.put(Delivery.Fields.phoneNumber.name(), delivery.getPhoneNumber());
        entityFields.put(Delivery.Fields.deliveryAddress.name(), outEntitySerializer.convertEntityToOutEntity(delivery.getDeliveryAddress()));
        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost().toString());
        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt().toString());
        entityFields.put(Delivery.Fields.deliveredAt.name(), delivery.getDeliveredAt());
    }
//    @Override
//    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
//        Delivery delivery = (Delivery) entity;
//        entityFields.put(Delivery.Fields.id.name(), delivery.getId());
//        entityFields.put(Delivery.Fields.order.name(), delivery.getOrder().getId());
//        entityFields.put(Delivery.Fields.phoneNumber.name(), delivery.getPhoneNumber());
//        entityFields.put(Delivery.Fields.deliveryAddress.name(), outEntitySerializer.convertEntityToOutEntity(delivery.getDeliveryAddress()));
//        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost());
//        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt());
//        entityFields.put(Delivery.Fields.deliveredAt.name(), delivery.getDeliveredAt());
//    }

}
