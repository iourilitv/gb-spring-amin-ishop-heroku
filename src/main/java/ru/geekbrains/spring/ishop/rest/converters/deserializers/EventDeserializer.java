package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventDeserializer implements IEntityDeserializer {
    private OutEntityDeserializer outEntityDeserializer;

    //To fixed "The dependencies of some of the beans in the application context form a cycle" problem
    @Autowired
    public void setOutEntityDeserializer(OutEntityDeserializer outEntityDeserializer) {
        this.outEntityDeserializer = outEntityDeserializer;
    }

    @Override
    public AbstractEntity recognize(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        isJsonEntityCorrect(jsonObject);
        Event event = Event.builder()
                .id(jsonObject.get(Event.Fields.id.name()).getAsLong())
                .actionType(recognizeActionType(jsonObject))
                .entityType(jsonObject.get(Event.Fields.entityType.name()).getAsString())
                .entityId(jsonObject.get(Event.Fields.entityId.name()).getAsLong())
                .createdAt(getLocalDateTimeOrNullIfAbsent(jsonObject.get(Event.Fields.createdAt.name())))
                .serverAcceptedAt(getLocalDateTimeOrNullIfAbsent(jsonObject.get(Event.Fields.serverAcceptedAt.name())))
                .build();
        return Optional.ofNullable(event).orElseThrow(() ->
                new OutEntityDeserializeException("Wrong json-object with entityType: " + EntityTypes.Event.name() + ". Can't complete deserialize process!"));

    }

    private ActionType recognizeActionType(JsonObject jsonObject) {
        JsonElement jsonElement = jsonObject.get(Event.Fields.actionType.name());
        return (ActionType) outEntityDeserializer.deserializeEntityFromOutEntityJson(Event.Fields.actionType.name(), jsonElement);
    }

    private LocalDateTime getLocalDateTimeOrNullIfAbsent(JsonElement jsonElement) {
        LocalDateTime localDateTime = null;
        if(!jsonElement.equals(JsonNull.INSTANCE)) {
            localDateTime = LocalDateTime.parse(jsonElement.getAsString());
        }
        return localDateTime;
    }

    private void isJsonEntityCorrect(JsonObject jsonObject) {
        Event.Fields[] fields = Event.Fields.values();
        for (Event.Fields field : fields) {
            if (jsonObject.get(field.name()) == null) {
                throw new OutEntityDeserializeException("Wrong json-object with entityType: " + EntityTypes.Event.name() + ". Can't complete deserialize process!");
            }
        }
    }

}