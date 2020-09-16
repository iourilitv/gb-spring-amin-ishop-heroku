package ru.geekbrains.spring.ishop.rest.deserializers;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class OutEntityDeserializer implements JsonDeserializer<OutEntity> {
    @Override
    public OutEntity deserialize(JsonElement json, Type typeOfT,
                                 JsonDeserializationContext context) throws JsonParseException
    {
        OutEntity outEntity = null;
        JsonObject jsonObject = json.getAsJsonObject();
        if(jsonObject.get(OutEntity.Fields.entityFields.name()) != null) {
            Set<Map.Entry<String, JsonElement>> fieldsSet = json.getAsJsonObject().get(OutEntity.Fields.entityFields.name()).getAsJsonObject().entrySet();
            Map<String, Object> entityFields = new HashMap<>();
            for (Map.Entry<String, JsonElement> jE: fieldsSet) {
                Object objectInternal;
                if(jE.getValue() instanceof JsonObject &&
                        jE.getValue().getAsJsonObject().get(OutEntity.Fields.entityFields.name()) != null) {
                    objectInternal = recognizeOutEntity(jE.getValue().toString());
                } else {
                    objectInternal = recognizeObject(jE.getValue());
                }
                entityFields.put(jE.getKey(), objectInternal);
            }
            outEntity = OutEntity.builder()
                    .entityType(json.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString())
                    .entityFields(entityFields)
                    .build();
        }
        return Optional.ofNullable(outEntity).orElseThrow(RuntimeException::new);
    }

    public OutEntity recognizeOutEntity (String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
                .create();
        return gson.fromJson(jsonString, OutEntity.class);
    }

    public Object recognizeObject(JsonElement jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Object.class);
    }

}