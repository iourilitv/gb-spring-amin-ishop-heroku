package ru.geekbrains.spring.ishop.rest.converters;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@Getter
public class SerializerFabric {
    private Map<String, IEntitySerializer> serializers;

    public void initSerializerFabric(Map<String, IEntitySerializer> serializers) {
        this.serializers = serializers;
    }

    public IEntitySerializer getSerializer(String entityType) {
        return serializers.get(entityType);
    }

}
