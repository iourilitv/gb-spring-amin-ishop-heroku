package ru.geekbrains.spring.ishop.utils.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.lang.reflect.Type;
import java.util.Map;

//source of example: https://howtodoinjava.com/gson/custom-serialization-deserialization/
@Component
public class OutEntityDeserializer implements JsonDeserializer<OutEntity> {
    @Override
    public OutEntity deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        Map<String,Object> fields = context.deserialize(
                jsonObject.get("body"), new TypeToken<Map<String, Object>>(){}.getType());

        return OutEntity.builder()
                .entityType(jsonObject.get("entityClassSimpleName").getAsString())
                .entityFields(fields)
                .build();
    }
}