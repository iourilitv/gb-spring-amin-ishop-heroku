package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSerializer implements IEntitySerializer {
    private final OutEntitySerializer outEntitySerializer;

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        User user = (User) entity;
        entityFields.put(User.Fields.id.name(), user.getId());
        entityFields.put(User.Fields.userName.name(), user.getUserName());
        entityFields.put(User.Fields.firstName.name(), user.getFirstName());
        entityFields.put(User.Fields.lastName.name(), user.getLastName());
        entityFields.put(User.Fields.phoneNumber.name(), user.getPhoneNumber());
        entityFields.put(User.Fields.email.name(), user.getEmail());
        if(user.getDeliveryAddress() != null) {
            entityFields.put(User.Fields.deliveryAddress.name(), outEntitySerializer.convertEntityToOutEntity(user.getDeliveryAddress()));
        }
    }
}
