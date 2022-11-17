package ru.geekbrains.spring.ishop.rest.converters;

import lombok.AllArgsConstructor;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;

import java.util.Map;

@AllArgsConstructor
public class DeserializerFactory {
    private final Map<String, IEntityDeserializer> deserializers;

    public IEntityDeserializer getDeserializer(String entityType) {
        return deserializers.get(entityType);
    }
}
