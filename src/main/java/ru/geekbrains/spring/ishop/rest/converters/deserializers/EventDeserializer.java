package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;

import java.time.LocalDateTime;

@Service
@Slf4j
//@RequiredArgsConstructor
public class EventDeserializer implements IEntityDeserializer {
    private OutEntityDeserializer outEntityDeserializer;

    //To fixed "The dependencies of some of the beans in the application context form a cycle" problem
    @Autowired
    public void setOutEntityDeserializer(OutEntityDeserializer outEntityDeserializer) {
        this.outEntityDeserializer = outEntityDeserializer;
    }
    //    private final ActionTypeDeserializer actionTypeDeserializer;

    @Override
    public AbstractEntity recognize(JsonElement json) {
        log.info("*** recognize().incoming json: " + json);

        JsonObject jsonObject = json.getAsJsonObject();

//        Set<Map.Entry<String, JsonElement>> fieldsSet = jsonObject.get(OutEntity.Fields.entityFields.name()).getAsJsonObject().entrySet();
//        Set<Map.Entry<String, JsonElement>> fieldsSet = jsonObject.entrySet();
//        for (Map.Entry<String, JsonElement> jE: fieldsSet) {
//            AbstractEntity field = null;
//            if(jE.getValue() instanceof JsonObject && outEntityDeserializer.isOutEntity(jE.getValue())) {
//                field = outEntityDeserializer.deserializeEntity(jE.getValue().getAsJsonObject());
//            }
//
//        }
        return Event.builder()
                .id(jsonObject.get(Event.Fields.id.name()).getAsLong())
                .actionType(recognizeActionType(jsonObject))
                .issuer(jsonObject.get(Event.Fields.issuer.name()).getAsString())
                .issuerEventId(jsonObject.get(Event.Fields.issuerEventId.name()).getAsLong())
                .entityType(jsonObject.get(Event.Fields.entityType.name()).getAsString())
                .entityId(jsonObject.get(Event.Fields.entityId.name()).getAsLong())
                .issuerCreatedAt(getLocalDateTimeOrNullIfAbsent(jsonObject.get(Event.Fields.issuerCreatedAt.name())))
                .recipientAcceptedAt(getLocalDateTimeOrNullIfAbsent(jsonObject.get(Event.Fields.recipientAcceptedAt.name())))
                .build();
    }

//    private ActionType recognizeActionType(JsonObject jsonObject) {
//        return (ActionType) actionTypeDeserializer.recognize(jsonObject.get(Event.Fields.actionType.name()));
//    }
    private ActionType recognizeActionType(JsonObject jsonObject) {
        JsonElement jsonElement = jsonObject.get(Event.Fields.actionType.name());
        log.info("*** recognizeActionType.jsonElement: " + jsonElement);

        return (ActionType) outEntityDeserializer.deserializeEntity(Event.Fields.actionType.name(), jsonElement);
    }

    private LocalDateTime getLocalDateTimeOrNullIfAbsent(JsonElement jsonElement) {
        log.info("*** getLocalDateTimeOrNullIfAbsent.jsonElement: " + jsonElement);

        LocalDateTime localDateTime = null;
//        if(jsonElement != null) {
        if(!jsonElement.equals(JsonNull.INSTANCE)) {
//            localDateTime = LocalDateTime.parse(String.valueOf(jsonElement));
//            LocalDateTime.parse(String.valueOf(jsonElement));
            localDateTime = LocalDateTime.parse(jsonElement.getAsString());
            log.info("*** getLocalDateTimeOrNullIfAbsent.localDateTime: " + localDateTime);
        }
        return localDateTime;
    }

//    @Override
//    public Event create(OutEntity outEntity) {
//        if(!outEntity.getEntityType().equals(EntityTypes.Event.name())) {
//            return null;
//        }
//        Map<String,Object> fields = outEntity.getEntityFields();
//        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
//        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.issuerEventId.name())));
//        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.entityId.name())));
//
//        OutEntity outEntityActionType = (OutEntity) fields.get(Event.Fields.actionType.name());
//        ActionType actionType = actionTypeBuilder.create(outEntityActionType);
//
//        return Event.builder()
//                .id((new Double(doubleId)).longValue())
//                .actionType(actionType)
//                .issuer((String) fields.get(Event.Fields.issuer.name()))
//                .issuerEventId((new Double(doubleIssuerEventId)).longValue())
//                .entityType(String.valueOf(fields.get(Event.Fields.entityType.name())))
//                .entityId((new Double(doubleEntityId)).longValue())
//                .issuerCreatedAt(getLocalDateTimeOrNullIfAbsent(fields.get(Event.Fields.issuerCreatedAt.name())))
//                .recipientAcceptedAt(getLocalDateTimeOrNullIfAbsent(fields.get(Event.Fields.recipientAcceptedAt.name())))
//                .build();
//    }
//
//    private LocalDateTime getLocalDateTimeOrNullIfAbsent(Object object) {
//        LocalDateTime localDateTime = null;
//        if(object != null) {
//            localDateTime = LocalDateTime.parse(String.valueOf(object));
//            LocalDateTime.parse(String.valueOf(object));
//        }
//        return localDateTime;
//    }

}