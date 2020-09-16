package ru.geekbrains.spring.ishop.rest.deserializers;

import com.google.gson.*;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//source of example: https://howtodoinjava.com/gson/custom-serialization-deserialization/
@Component
public class OutEntityDeserializer implements JsonDeserializer<OutEntity> {
//    Gson gsonOutEntity = new GsonBuilder()
//            .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
//            .create();

//    @Override
//    public OutEntity deserialize(JsonElement json, Type typeOfT,
//                                JsonDeserializationContext context) throws JsonParseException
//    {
//        JsonObject jsonObject = json.getAsJsonObject();
//        Map<String,Object> fields = context.deserialize(
//                jsonObject.get(OutEntity.Fields.entityFields.name()),
//                new TypeToken<Map<String, Object>>(){}.getType());
//        return OutEntity.builder()
//                .entityType(jsonObject.get(OutEntity.Fields.entityType.name()).getAsString())
//                .entityFields(fields)
//                .build();
//    }
//    @Override
//    public OutEntity deserialize(JsonElement json, Type typeOfT,
//                                 JsonDeserializationContext context) throws JsonParseException
//    {
//        OutEntity outEntity = OutEntity.nullObject;
//        JsonObject jsonObject = json.getAsJsonObject();
//        JsonElement jsonElementEntityType = jsonObject.get(OutEntity.Fields.entityType.name());
//        JsonElement jsonElementEntityFields = jsonObject.get(OutEntity.Fields.entityFields.name());
//        if(jsonElementEntityType != null && jsonElementEntityFields != null) {
//            Map<String,Object> fields = new HashMap<>();
//            for (Map.Entry<String, JsonElement> jsonElementField: jsonElementEntityFields.getAsJsonObject().entrySet()) {
//                JsonObject jsonObject2 = jsonElementField.getValue().getAsJsonObject();
//                JsonElement jsonElementEntityType2 = jsonObject2.get(OutEntity.Fields.entityType.name());
//                JsonElement jsonElementEntityFields2 = jsonObject2.get(OutEntity.Fields.entityFields.name());
//                OutEntity outEntity2;
//                if(jsonElementEntityType2 != null && jsonElementEntityFields2 != null) {
//                    outEntity2 = gsonOutEntity.fromJson((JsonElement) jsonElementField, OutEntity.class);
//                    System.out.println(outEntity);
//                } else {
//                    //jsonElementEntityFields.getAsJsonObject().entrySet()
////                    Map<String,Object> fields = context.deserialize(jsonElementEntityFields, new TypeToken<Map<String, Object>>(){}.getType());
//
//
//
//
//                    outEntity = OutEntity.builder()
//                            .entityType(jsonElementEntityType.getAsString())
//                            .entityFields(fields)
//                            .build();
//
//                }
//            }
//
//        }
//        return outEntity;
//    }
//    @Override
//    public OutEntity deserialize(JsonElement json, Type typeOfT,
//                                          JsonDeserializationContext context) throws JsonParseException
//    {
//        System.out.println("incoming.json: " + json);
//
//        OutEntity outEntity = null;
//        JsonObject jsonObject = json.getAsJsonObject();
////        JsonElement jsonElementEntityType = jsonObject.get(OutEntity.Fields.entityType.name());
////        OutEntity outEntity = OutEntity.builder()
////                .entityType(jsonElementEntityType.getAsString())
////                .build();
////        OutEntity outEntity = OutEntity.builder()
////                .entityType(json.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString())
////                .build();
////        if(jsonObject.get(OutEntity.Fields.entityType.name()) != null && jsonObject.get(OutEntity.Fields.entityFields.name()) != null) {
//
////        System.out.println("jsonObject.get(OutEntity.Fields.entityFields.name()): " + jsonObject.get(OutEntity.Fields.entityFields.name()));
//
//        if(jsonObject.get(OutEntity.Fields.entityFields.name()) != null) {
//            Set<Map.Entry<String, JsonElement>> fieldsSet = json.getAsJsonObject().get(OutEntity.Fields.entityFields.name()).getAsJsonObject().entrySet();
//            Map<String, Object> entityFields = new HashMap<>();
//            for (Map.Entry<String, JsonElement> jE: fieldsSet) {
////            JsonObject jsonObject = jE.getValue().getAsJsonObject();
////            JsonElement jsonElementEntityType = jsonObject.get(OutEntity.Fields.entityType.name());
////            JsonElement jsonElementEntityFields = jsonObject.get(OutEntity.Fields.entityFields.name());
//                System.out.println("jE: " + jE);
//
//                JsonElement jsonElementEntityFields = jE.getValue().getAsJsonObject().get(OutEntity.Fields.entityFields.name());
//                Object objectInternal;
//                if(jsonElementEntityFields != null) {
////            if(jsonElementEntityType != null && jsonElementEntityFields != null) {
////                OutEntity outEntityInternal = gsonOutEntity.fromJson((JsonElement) jsonElementField, OutEntity.class);
////                System.out.println(outEntityInternal);
////                fields.put(jsonElementField.getKey(), outEntityInternal);
////                objectInternal = gsonOutEntity.fromJson((JsonElement) jE, OutEntity.class);
////                objectInternal = recognizeOutEntity((JsonElement) jE);
//                    objectInternal = recognizeOutEntity(jE.getValue());
//                } else {
//                    objectInternal = jE.getValue();
////                fields.put(jsonElementField.getKey(), jsonElementField.getValue());
//                    //jsonElementEntityFields.getAsJsonObject().entrySet()
////                    Map<String,Object> fields = context.deserialize(jsonElementEntityFields, new TypeToken<Map<String, Object>>(){}.getType());
//
//
//
//
////                    outEntity = OutEntity.builder()
////                            .entityType(jsonElementEntityType.getAsString())
////                            .entityFields(fields)
////                            .build();
//
//                }
//                entityFields.put(jE.getKey(), objectInternal);
//            }
//            outEntity = OutEntity.builder()
//                    .entityType(json.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString())
//                    .entityFields(entityFields)
//                    .build();
//
//            System.out.println("outEntity: " + outEntity);
//            System.out.println("Map.entityFields: " + entityFields);
//        }
////        if(jsonElementEntityType != null && jsonElementEntityFields != null) {
////            Map<String,Object> fields = new HashMap<>();
////            for (Map.Entry<String, JsonElement> jsonElementField: jsonElementEntityFields.getAsJsonObject().entrySet()) {
////                JsonObject jsonObject2 = jsonElementField.getValue().getAsJsonObject();
////                JsonElement jsonElementEntityType2 = jsonObject2.get(OutEntity.Fields.entityType.name());
////                JsonElement jsonElementEntityFields2 = jsonObject2.get(OutEntity.Fields.entityFields.name());
////                OutEntity outEntity2;
////                if(jsonElementEntityType2 != null && jsonElementEntityFields2 != null) {
////                    outEntity2 = gsonOutEntity.fromJson((JsonElement) jsonElementField, OutEntity.class);
////                    System.out.println(outEntity);
////                } else {
////                    //jsonElementEntityFields.getAsJsonObject().entrySet()
//////                    Map<String,Object> fields = context.deserialize(jsonElementEntityFields, new TypeToken<Map<String, Object>>(){}.getType());
////
////
////
////
//////                    outEntity = OutEntity.builder()
//////                            .entityType(jsonElementEntityType.getAsString())
//////                            .entityFields(fields)
//////                            .build();
////
////                }
////            }
////        }
//
//        //TODO Replace RuntimeException with OutEntityDeserializeException(create)
//        // to catch com.google.gson.JsonSyntaxException
//        return Optional.ofNullable(outEntity).orElseThrow(RuntimeException::new);
//    }
    @Override
    public OutEntity deserialize(JsonElement json, Type typeOfT,
                                 JsonDeserializationContext context) throws JsonParseException
    {
//        System.out.println("incoming.json: " + json);

        OutEntity outEntity = null;
        JsonObject jsonObject = json.getAsJsonObject();
        if(jsonObject.get(OutEntity.Fields.entityFields.name()) != null) {
            Set<Map.Entry<String, JsonElement>> fieldsSet = json.getAsJsonObject().get(OutEntity.Fields.entityFields.name()).getAsJsonObject().entrySet();
            Map<String, Object> entityFields = new HashMap<>();
            for (Map.Entry<String, JsonElement> jE: fieldsSet) {

//                System.out.println("jE: " + jE);
                System.out.println("OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): " + OutEntity.Fields.entityFields.name());
                System.out.println("OutEntityDeserializer.deserialize().jE.getKey(): " + jE.getKey());
                System.out.println("OutEntityDeserializer.deserialize().jE.getValue(): " + jE.getValue());

                Object objectInternal;
//                if(jE.getValue().equals(OutEntity.Fields.entityFields.name())) {
                if(jE instanceof JsonElement &&
                        jE.getValue().getAsJsonObject().get(OutEntity.Fields.entityFields.name()).getAsString()
                                .equals(OutEntity.Fields.entityFields.name())) {
                    objectInternal = recognizeOutEntity(jE.getValue());
                } else {
//                    objectInternal = jE.getValue();
                    objectInternal = recognizeObject(jE.getValue());
                }

                System.out.println("OutEntityDeserializer.deserialize().objectInternal: " + objectInternal);

                entityFields.put(jE.getKey(), objectInternal);

                System.out.println("OutEntityDeserializer.deserialize().Map.entityFields: " + entityFields);
            }
            outEntity = OutEntity.builder()
                    .entityType(json.getAsJsonObject().get(OutEntity.Fields.entityType.name()).getAsString())
                    .entityFields(entityFields)
                    .build();

            System.out.println("outEntity: " + outEntity);
//            System.out.println("Map.entityFields: " + entityFields);
        }
        return Optional.ofNullable(outEntity).orElseThrow(RuntimeException::new);
    }

    public OutEntity recognizeOutEntity (JsonElement jsonString) {

//        System.out.println("recognizeOutEntity().jsonString: " + jsonString);

        Gson gsonOutEntity = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
                .create();
        return gsonOutEntity.fromJson(jsonString, OutEntity.class);
    }

    public Object recognizeObject(JsonElement jsonString) {
        Gson gsonOutEntity = new Gson();

        return gsonOutEntity.fromJson(jsonString, Object.class);
    }

//    private JsonElement getJsonElement(JsonElement json) {
//        JsonElement out;
//        JsonObject jsonObject = json.getAsJsonObject();
//        JsonElement jsonElementEntityType = jsonObject.get(OutEntity.Fields.entityType.name());
//        JsonElement jsonElementEntityFields = jsonObject.get(OutEntity.Fields.entityFields.name());
//        if (jsonElementEntityType != null && jsonElementEntityFields != null) {
//            out = json.getAsJsonObject().
//        }
//
//    }

//    private Map<String,Object> recognizeEntityFields(JsonElement jsonElementEntityFields) {
//        Map<String,Object> fields = context.deserialize(jsonElementEntityFields, new TypeToken<Map<String, Object>>(){}.getType());
//        Gson gsonOutEntity = new GsonBuilder()
//                .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
//                .create();
//        JsonElement jsonElementFields = context.deserialize(
//                jsonObject.get(OutEntity.Fields.entityFields.name()),
//                JsonElement.class);
//        outEntity = gsonOutEntity.fromJson(jsonElementFields, OutEntity.class);
//    }
}