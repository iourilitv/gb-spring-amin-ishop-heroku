package ru.geekbrains.spring.ishop.rest.converters;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.ActionTypeDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.EventDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class DeserializerFabric {
    private final Map<String, IEntityDeserializer> deserializerMap;
    private final EventDeserializer eventDeserializer;
    private final ActionTypeDeserializer actionTypeDeserializer;

    @Autowired
    public DeserializerFabric(EventDeserializer eventDeserializer, ActionTypeDeserializer actionTypeDeserializer) {
        this.eventDeserializer = eventDeserializer;
        this.actionTypeDeserializer = actionTypeDeserializer;
        //TODO наполнить мапу автоматически из пакета rest.converters.deserializers??
        deserializerMap = new HashMap<>();
        deserializerMap.put(EntityTypes.Event.name(), this.eventDeserializer);
        deserializerMap.put(EntityTypes.ActionType.name(), this.actionTypeDeserializer);

    }

    public IEntityDeserializer getDeserializer(String entityType) {
        return deserializerMap.get(entityType);
    }

}
