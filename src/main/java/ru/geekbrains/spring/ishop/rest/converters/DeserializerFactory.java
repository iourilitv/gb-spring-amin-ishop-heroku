package ru.geekbrains.spring.ishop.rest.converters;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;

import java.util.Map;

@Service
@Getter
public class DeserializerFactory {
    private Map<String, IEntityDeserializer> deserializers;

    public void initDeserializerFactory(Map<String, IEntityDeserializer> deserializers) {
        this.deserializers = deserializers;
    }

    public IEntityDeserializer getDeserializer(String entityType) {
        return deserializers.get(entityType);
    }

}
