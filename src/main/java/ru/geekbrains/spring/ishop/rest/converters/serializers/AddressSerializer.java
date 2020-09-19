package ru.geekbrains.spring.ishop.rest.converters.serializers;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
public class AddressSerializer implements IEntitySerializer {
    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Address address = (Address) entity;
        entityFields.put(Address.Fields.id.name(), address.getId());
        entityFields.put(Address.Fields.country.name(), address.getCountry());
        entityFields.put(Address.Fields.city.name(), address.getCity());
        entityFields.put(Address.Fields.address.name(), address.getAddress());
    }
}
