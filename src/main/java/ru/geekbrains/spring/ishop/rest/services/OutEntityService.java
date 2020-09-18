package ru.geekbrains.spring.ishop.rest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFabric;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFabric;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.ActionTypeDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.EventDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.ActionTypeSerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.EventSerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.OutEntitySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.*;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class OutEntityService {
    private final OutEntityDeserializer outEntityDeserializer;
    private final EventDeserializer eventDeserializer;
    private final ActionTypeDeserializer actionTypeDeserializer;
    private final DeserializerFabric deserializerFabric;

    private final OutEntitySerializer outEntitySerializer;
    private final EventSerializer eventSerializer;
    private final ActionTypeSerializer actionTypeSerializer;
    private final SerializerFabric serializerFabric;

    public OutEntity convertEntityToOutEntity(AbstractEntity entity) {
        return outEntitySerializer.convertEntityToOutEntity(entity);
    }

    //TODO For Studding and Testing only
    public AbstractEntity recognizeEntityFromOutEntityJsonString(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, outEntityDeserializer)
                .create();
        OutEntity outEntity = gson.fromJson(jsonString, OutEntity.class);
        JsonElement jsonElement = (JsonElement) outEntity.getEntityFields().get(OutEntity.Fields.entityFields.name());
        AbstractEntity entity = outEntityDeserializer
                .deserializeEntityFromOutEntityJson(outEntity.getEntityType(), jsonElement);
        return Optional.ofNullable(entity).orElseThrow(() ->
                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
    }

    //TODO сделать автоматическое наполнение множества из пакета deserializers
    @PostConstruct
    private void initDeserializers() {
        Map<String, IEntityDeserializer> deserializers = new HashMap<>();
        deserializers.put(EntityTypes.Event.name(), this.eventDeserializer);
        deserializers.put(EntityTypes.ActionType.name(), this.actionTypeDeserializer);
        deserializerFabric.initDeserializerFabric(deserializers);
        outEntityDeserializer.setDeserializerFabric(deserializerFabric);
    }

    //TODO сделать автоматическое наполнение множества из пакета serializers
    @PostConstruct
    private void initSerializers() {
        Map<String, IEntitySerializer> serializers = new HashMap<>();
        serializers.put(EntityTypes.Event.name(), this.eventSerializer);
        serializers.put(EntityTypes.ActionType.name(), this.actionTypeSerializer);
        serializerFabric.initSerializerFabric(serializers);
        outEntitySerializer.setSerializerFabric(serializerFabric);
    }

}
