package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFabric;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OutEntityDeserializer implements JsonDeserializer<OutEntity> {
    private DeserializerFabric deserializerFabric;

    public void setDeserializerFabric(DeserializerFabric deserializerFabric) {
        this.deserializerFabric = deserializerFabric;
    }

    @Override
    public OutEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(!isOutEntity(json)) {
            throw new OutEntityDeserializeException("Wrong the first OutEntity. Missing or invalid the fields: " +
                    OutEntity.Fields.entityType.name() + " or " + OutEntity.Fields.entityFields.name() +
                    " in json object!");
        }
        Map<String, Object> map = new HashMap<>();
        map.put(OutEntity.Fields.entityFields.name(), getEntityFields(json));
        OutEntity outEntity = OutEntity.builder()
                .entityType(getEntityType(json).getAsString())
                .entityFields(map)
                .build();
        return Optional.ofNullable(outEntity).orElseThrow(() ->
                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
    }

    public AbstractEntity deserializeEntityFromOutEntityJson(String entityType, JsonElement jsonElement) {

        log.info("*** deserializeEntityFromOutEntityJson().deserializerFabric.getDeserializer(entityType): " + deserializerFabric.getDeserializer(entityType));

        AbstractEntity entity = null;
        if(isOutEntity(jsonElement)) {
            entityType = getEntityType(jsonElement).getAsString();
            jsonElement = getEntityFields(jsonElement);
        }
        EntityTypes[] entityTypes = EntityTypes.values();
        for (EntityTypes type : entityTypes) {
            if (entityType.equals(type.name())) {
                entity = deserializerFabric.getDeserializer(entityType).recognize(jsonElement);
            }
        }
        String finalEntityType = entityType;
        return Optional.ofNullable(entity).orElseThrow(() ->
                new OutEntityDeserializeException("Wrong json-object with entityType: " + finalEntityType + ". Can't complete deserialize process!"));
    }

    private boolean isOutEntity(JsonElement json) {
        return getEntityFields(json) != null && getEntityType(json) != null;
    }

    private JsonElement getEntityType(JsonElement json) {
        return json.getAsJsonObject().get(OutEntity.Fields.entityType.name());
    }

    private JsonElement getEntityFields(JsonElement json) {
        return json.getAsJsonObject().get(OutEntity.Fields.entityFields.name());
    }

}