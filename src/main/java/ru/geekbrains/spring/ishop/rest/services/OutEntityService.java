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
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.*;
import ru.geekbrains.spring.ishop.rest.converters.serializers.*;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class OutEntityService {
    public enum Converters {deserializer, serializer}

    private final OutEntityDeserializer outEntityDeserializer;
    private final DeserializerFactory deserializerFactory;
    private final OutEntitySerializer outEntitySerializer;
    private final SerializerFactory serializerFactory;
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
        Set<String> keys = deserializersBeans.keySet();
        for (String key :keys) {
            for (EntityTypes type : EntityTypes.values()) {
                String modifiedKey = key.toLowerCase().replace(Converters.deserializer.name(), "");
                if(modifiedKey.equals(type.name().toLowerCase())) {
                    deserializers.put(type.name(), deserializersBeans.get(key));
                    break;
                }
            }
        }
        deserializerFactory.initDeserializerFactory(deserializers);
        outEntityDeserializer.setDeserializerFactory(deserializerFactory);
    }

    @PostConstruct
    private void initSerializers() {
        Map<String, IEntitySerializer> serializersBeans = context.getBeansOfType(IEntitySerializer.class);
        Map<String, IEntitySerializer> serializers = new HashMap<>();
        Set<String> keys = serializersBeans.keySet();
        for (String key :keys) {
            for (EntityTypes type : EntityTypes.values()) {
                String modifiedKey = key.toLowerCase().replace(Converters.serializer.name(), "");
                if(modifiedKey.equals(type.name().toLowerCase())) {
                    serializers.put(type.name(), serializersBeans.get(key));
                    break;
                }
            }
        }
        serializerFactory.initSerializerFactory(serializers);
        outEntitySerializer.setSerializerFactory(serializerFactory);
    }

}
