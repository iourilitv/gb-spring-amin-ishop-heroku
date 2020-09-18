package ru.geekbrains.spring.ishop.rest.converters;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;

import java.util.Map;

@Service
@Getter
public class DeserializerFabric {
    private Map<String, IEntityDeserializer> deserializers;

    public void initDeserializerFabric(Map<String, IEntityDeserializer> deserializers) {
        this.deserializers = deserializers;
    }

    public IEntityDeserializer getDeserializer(String entityType) {
        return deserializers.get(entityType);
    }

}
