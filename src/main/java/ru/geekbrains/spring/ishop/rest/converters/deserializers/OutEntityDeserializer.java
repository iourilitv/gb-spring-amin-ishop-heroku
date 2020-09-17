package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFabric;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
//@RequiredArgsConstructor
//public class OutEntityDeserializer implements JsonDeserializer<AbstractEntity> {
public class OutEntityDeserializer implements JsonDeserializer<OutEntity> {
//    private final DeserializerFabric deserializerFabric;
    private DeserializerFabric deserializerFabric;

    //To fixed "The dependencies of some of the beans in the application context form a cycle" problem
    @Autowired
    public void setDeserializerFabric(DeserializerFabric deserializerFabric) {
        this.deserializerFabric = deserializerFabric;
    }

    //    @Override
//    public AbstractEntity deserialize(JsonElement json, Type typeOfT,
//                                 JsonDeserializationContext context) throws JsonParseException
//    {
//        log.info("*** deserialize().incoming json: " + json);
//        JsonObject jsonObject = json.getAsJsonObject();
//        AbstractEntity entity = deserializeEntity(jsonObject);
//        //TODO Replace RuntimeException with OutEntityDeserializeException(create)
//        // to catch com.google.gson.JsonSyntaxException
//        return Optional.ofNullable(entity).orElseThrow(RuntimeException::new);
//    }
    @Override
    public OutEntity deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException
    {
//        log.info("*** deserialize().incoming json: " + json);

        JsonObject jsonObject = json.getAsJsonObject();
        Map<String, Object> map = new HashMap<>();
        map.put(OutEntity.Fields.entityFields.name(), jsonObject.get(OutEntity.Fields.entityFields.name()));
        OutEntity outEntity = OutEntity.builder()
                    .entityType(jsonObject.get(OutEntity.Fields.entityType.name()).getAsString())
                    .entityFields(map)
                    .build();

        //TODO Replace RuntimeException with OutEntityDeserializeException(create)
        // to catch com.google.gson.JsonSyntaxException
        return Optional.ofNullable(outEntity).orElseThrow(RuntimeException::new);
    }

    public AbstractEntity recognizeEntity(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, this)
                .create();
        OutEntity outEntity = gson.fromJson(jsonString, OutEntity.class);
        JsonElement jsonElement = (JsonElement) outEntity.getEntityFields().get(OutEntity.Fields.entityFields.name());
        AbstractEntity entity = deserializeEntity(outEntity.getEntityType(), jsonElement);
        return Optional.ofNullable(entity).orElseThrow(RuntimeException::new);
    }

//    public Object recognizeObject(JsonElement jsonString) {
//        Gson gson = new Gson();
//        return gson.fromJson(jsonString, Object.class);
//    }

//    public AbstractEntity deserializeEntity(JsonObject jsonObject) {
//        AbstractEntity entity = null;
//        String entityType = jsonObject.get(OutEntity.Fields.entityType.name()).getAsString();
//
//        log.info("*** deserializeEntity().entityType: " + entityType);
//        JsonElement elementEntityFields = jsonObject.get(OutEntity.Fields.entityFields.name());
//        log.info("*** deserializeEntity().elementEntityFields: " + elementEntityFields);
//        EntityTypes[] entityTypes = EntityTypes.values();
//        for (EntityTypes type : entityTypes) {
//            if (entityType.equals(type.name())) {
//                entity = deserializerFabric.getDeserializer(entityType)
////                        .recognize(jsonObject.get(OutEntity.Fields.entityFields.name()).toString());
////                        .recognize(jsonObject.get(OutEntity.Fields.entityFields.name()));
//                .recognize(elementEntityFields);
//            }
//        }
//        return entity;
//    }
    public AbstractEntity deserializeEntity(String entityType, JsonElement jsonElement) {
        AbstractEntity entity = null;

        log.info("*** INCOMING deserializeEntity().entityType: " + entityType);
        log.info("*** INCOMING deserializeEntity().jsonElement: " + jsonElement);

        if(isOutEntity(jsonElement)) {
            entityType = jsonElement.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString();
            jsonElement = jsonElement.getAsJsonObject().get(OutEntity.Fields.entityFields.name());
        }

        log.info("*** UPDATED deserializeEntity().entityType: " + entityType);
        log.info("*** UPDATED deserializeEntity().jsonElement: " + jsonElement);

        EntityTypes[] entityTypes = EntityTypes.values();
        for (EntityTypes type : entityTypes) {
            if (entityType.equals(type.name())) {

                log.info("*** deserializeEntity().entityTypes[].type: " + type);

                entity = deserializerFabric.getDeserializer(entityType)
//                        .recognize(jsonObject.get(OutEntity.Fields.entityFields.name()).toString());
//                        .recognize(jsonObject.get(OutEntity.Fields.entityFields.name()));
                        .recognize(jsonElement);
            }
        }
        return entity;
    }

    public boolean isOutEntity(JsonElement json) {
        return json.getAsJsonObject().get(OutEntity.Fields.entityFields.name()) != null;
    }

}