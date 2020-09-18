package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    //To fixed "The dependencies of some of the beans in the application context form a cycle" problem
    @Autowired
    public void setDeserializerFabric(DeserializerFabric deserializerFabric) {
        this.deserializerFabric = deserializerFabric;
    }

//    public AbstractEntity recognizeEntityFromOutEntityJsonString(String jsonString) {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(OutEntity.class, this)
//                .create();
//        OutEntity outEntity = gson.fromJson(jsonString, OutEntity.class);
//        JsonElement jsonElement = (JsonElement) outEntity.getEntityFields().get(OutEntity.Fields.entityFields.name());
//        AbstractEntity entity = deserializeEntity(outEntity.getEntityType(), jsonElement);
//        return Optional.ofNullable(entity).orElseThrow(RuntimeException::new);
//    }
    public AbstractEntity recognizeEntityFromOutEntityJsonString(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, this)
                .create();
        OutEntity outEntity = gson.fromJson(jsonString, OutEntity.class);
        JsonElement jsonElement = (JsonElement) outEntity.getEntityFields().get(OutEntity.Fields.entityFields.name());
        AbstractEntity entity = deserializeEntityFromOutEntityJson(outEntity.getEntityType(), jsonElement);
        return Optional.ofNullable(entity).orElseThrow(() ->
                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
    }

//    @Override
//    public OutEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject jsonObject = json.getAsJsonObject();
//        Map<String, Object> map = new HashMap<>();
//        map.put(OutEntity.Fields.entityFields.name(), jsonObject.get(OutEntity.Fields.entityFields.name()));
//        OutEntity outEntity = OutEntity.builder()
//                    .entityType(jsonObject.get(OutEntity.Fields.entityType.name()).getAsString())
//                    .entityFields(map)
//                    .build();
//
//        //TODO Replace RuntimeException with OutEntityDeserializeException(create)
//        // to catch com.google.gson.JsonSyntaxException/JsonParseException
//        return Optional.ofNullable(outEntity).orElseThrow(() ->
//                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
//    }
//    @Override
//    public OutEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
////        JsonObject jsonObject = json.getAsJsonObject();
//        JsonElement jsonElementFields = getEntityFields(json);
//        if(jsonElementFields == null) {
//            throw new OutEntityDeserializeException("Missing or invalid " + OutEntity.Fields.entityFields.name() + " field in json object!");
//        }
//        Map<String, Object> map = new HashMap<>();
////        map.put(OutEntity.Fields.entityFields.name(), jsonObject.get(OutEntity.Fields.entityFields.name()));
//        map.put(OutEntity.Fields.entityFields.name(), jsonElementFields);
//        OutEntity outEntity = OutEntity.builder()
////                .entityType(jsonObject.get(OutEntity.Fields.entityType.name()).getAsString())
//                .entityType(getEntityType(json).getAsString())
//                .entityFields(map)
//                .build();
//        return Optional.ofNullable(outEntity).orElseThrow(() ->
//                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
//    }
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

//        log.info("*** deserialize.FINAL outEntity: " + outEntity);

        return Optional.ofNullable(outEntity).orElseThrow(() ->
                new OutEntityDeserializeException("Something wrong happened during incoming json-object deserialize process!"));
    }

//    public AbstractEntity deserializeEntity(String entityType, JsonElement jsonElement) {
//        AbstractEntity entity = null;
//        if(isOutEntity(jsonElement)) {
//            entityType = jsonElement.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString();
//            jsonElement = jsonElement.getAsJsonObject().get(OutEntity.Fields.entityFields.name());
//        }
//        EntityTypes[] entityTypes = EntityTypes.values();
//        for (EntityTypes type : entityTypes) {
//            if (entityType.equals(type.name())) {
//                entity = deserializerFabric.getDeserializer(entityType).recognize(jsonElement);
//            }
//        }
//        return entity;
//    }
//    public AbstractEntity deserializeEntityFromOutEntityJson(String entityType, JsonElement jsonElement) {
//        AbstractEntity entity = null;
//        JsonElement jsonElementFields = getEntityFields(jsonElement);
//        if(jsonElementFields != null) {
//            entityType = getEntityType(jsonElement).getAsString();
//            jsonElement = jsonElementFields;
//        }
//
//        log.info("*** deserializeEntityFromOutEntityJson.jsonElement: " + jsonElement);
//
//        EntityTypes[] entityTypes = EntityTypes.values();
//        for (EntityTypes type : entityTypes) {
//            if (entityType.equals(type.name())) {
//                entity = deserializerFabric.getDeserializer(entityType).recognize(jsonElement);
//            }
//        }
//        return entity;
//    }
    public AbstractEntity deserializeEntityFromOutEntityJson(String entityType, JsonElement jsonElement) {
        log.info("*** deserializeEntityFromOutEntityJson.INCOMING jsonElement: " + jsonElement);
        log.info("*** deserializeEntityFromOutEntityJson.entityType: " + entityType);
        log.info("*** deserializeEntityFromOutEntityJson.INCOMING isOutEntity(jsonElement): " + isOutEntity(jsonElement));

        AbstractEntity entity = null;
        if(isOutEntity(jsonElement)) {
            entityType = getEntityType(jsonElement).getAsString();
            jsonElement = getEntityFields(jsonElement);
        }

        log.info("*** deserializeEntityFromOutEntityJson.jsonElement: " + jsonElement);

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
//    private boolean isOutEntity(JsonElement json) {
//        return getEntityFields(json) != null;
//    }
//    private boolean isOutEntity(JsonElement json) {
//        return json.getAsJsonObject().get(OutEntity.Fields.entityFields.name()) != null;
//    }
//    private JsonElement getEntityType(JsonElement json) {
//        return Optional.ofNullable(
//                json.getAsJsonObject().get(OutEntity.Fields.entityType.name())).orElseThrow(() ->
//                new OutEntityDeserializeException("Missing or invalid " + OutEntity.Fields.entityType.name() + " field in json object!"));
//    }
    private JsonElement getEntityType(JsonElement json) {
        return json.getAsJsonObject().get(OutEntity.Fields.entityType.name());
    }

//    private JsonElement getEntityFields(JsonElement json) {
//        return Optional.ofNullable(
//                json.getAsJsonObject().get(OutEntity.Fields.entityFields.name())).orElseThrow(() ->
//                new OutEntityDeserializeException("Missing or invalid " + OutEntity.Fields.entityFields.name() + " field in json object!"));
//    }
//    private JsonElement getEntityFields(JsonElement json) {
//        JsonElement out = Optional.ofNullable(
//                json.getAsJsonObject().get(OutEntity.Fields.entityFields.name())).orElse(null);
//        return Optional.of(out).orElseThrow(() ->
//                new OutEntityDeserializeException("Missing or invalid " + OutEntity.Fields.entityFields.name() + " field in json object!"));
//    }
    private JsonElement getEntityFields(JsonElement json) {
        return json.getAsJsonObject().get(OutEntity.Fields.entityFields.name());
    }

}