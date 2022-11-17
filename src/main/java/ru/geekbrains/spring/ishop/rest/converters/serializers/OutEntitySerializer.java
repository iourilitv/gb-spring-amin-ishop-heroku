package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.AllArgsConstructor;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.exception.OutEntitySerializeException;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class OutEntitySerializer {
    private final SerializerFactory factory;

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
                IEntitySerializer serializer = factory.getSerializer(entityType);
                if(serializer != null) {
                    serializer.fillEntityFields(entity, entityFields);
                } else {
                    throw new OutEntitySerializeException("No serializer for " + entityType + ". Can't completed serialization!");
                }
            }
        }
        return out;
    }
}
