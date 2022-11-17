package ru.geekbrains.spring.ishop.rest.converters;

import lombok.AllArgsConstructor;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@AllArgsConstructor
public class SerializerFactory {
    private final Map<String, IEntitySerializer> serializers;

    public IEntitySerializer getSerializer(String entityType) {
        return serializers.get(entityType);
    }
}
