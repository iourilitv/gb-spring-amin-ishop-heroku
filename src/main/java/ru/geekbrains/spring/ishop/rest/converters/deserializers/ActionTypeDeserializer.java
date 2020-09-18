package ru.geekbrains.spring.ishop.rest.converters.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.util.Optional;

@Service
@Slf4j
public class ActionTypeDeserializer implements IEntityDeserializer {
    @Override
    public AbstractEntity recognize(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        isJsonEntityCorrect(jsonObject);
        ActionType actionType = ActionType.builder()
                .id(jsonObject.get(ActionType.Fields.id.name()).getAsShort())
                .title(jsonObject.get(ActionType.Fields.title.name()).getAsString())
                .description(jsonObject.get(ActionType.Fields.description.name()).getAsString())
                .entityType(jsonObject.get(ActionType.Fields.entityType.name()).getAsString())
                .build();
        return Optional.ofNullable(actionType).orElseThrow(() ->
                new OutEntityDeserializeException("Wrong json-object with entityType: " + EntityTypes.ActionType.name() + ". Can't complete deserialize process!"));
    }

    private void isJsonEntityCorrect(JsonObject jsonObject) {
        ActionType.Fields[] fields = ActionType.Fields.values();
        for (ActionType.Fields field : fields) {
            if (jsonObject.get(field.name()) == null) {
                throw new OutEntityDeserializeException("Wrong json-object with entityType: " + EntityTypes.ActionType.name() + ". Can't complete deserialize process!");
            }
        }
    }
}
