package ru.geekbrains.spring.ishop.rest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFabric;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFabric;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
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
    private final DeserializerFabric deserializerFabric;
    private final OutEntitySerializer outEntitySerializer;
    private final SerializerFabric serializerFabric;
    private final ApplicationContext context;

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

    @PostConstruct
    private void initDeserializers() {
        Map<String, IEntityDeserializer> deserializersBeans = context.getBeansOfType(IEntityDeserializer.class);
        Map<String, IEntityDeserializer> deserializers = new HashMap<>();

        //TODO переписать без дублирования
        Set<String> keys = deserializersBeans.keySet();
        for (String key :keys) {
            for (EntityTypes type : EntityTypes.values()) {
                String modifiedKey = key.toLowerCase().replace("deserializer", "");
                if(modifiedKey.equals(type.name().toLowerCase())) {
                    deserializers.put(type.name(), deserializersBeans.get(key));
                    break;
                }
            }
        }
        deserializerFabric.initDeserializerFabric(deserializers);
        outEntityDeserializer.setDeserializerFabric(deserializerFabric);
    }

    @PostConstruct
    private void initSerializers() {
        Map<String, IEntitySerializer> serializersBeans = context.getBeansOfType(IEntitySerializer.class);
        Map<String, IEntitySerializer> serializers = new HashMap<>();

        //TODO переписать без дублирования
        Set<String> keys = serializersBeans.keySet();
        for (String key :keys) {
            for (EntityTypes type : EntityTypes.values()) {
                String modifiedKey = key.toLowerCase().replace("serializer", "");
                if(modifiedKey.equals(type.name().toLowerCase())) {
                    serializers.put(type.name(), serializersBeans.get(key));
                    break;
                }
            }
        }
        serializerFabric.initSerializerFabric(serializers);
        outEntitySerializer.setSerializerFabric(serializerFabric);
    }

}
