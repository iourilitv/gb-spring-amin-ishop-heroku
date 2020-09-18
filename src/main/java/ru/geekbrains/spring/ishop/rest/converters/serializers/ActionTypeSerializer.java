package ru.geekbrains.spring.ishop.rest.converters.serializers;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
public class ActionTypeSerializer implements IEntitySerializer {
    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        ActionType actionType = (ActionType) entity;
        entityFields.put(ActionType.Fields.id.name(), actionType.getId());
        entityFields.put(ActionType.Fields.title.name(), actionType.getTitle());
        entityFields.put(ActionType.Fields.description.name(), actionType.getDescription());
        entityFields.put(ActionType.Fields.entityType.name(), actionType.getEntityType());
    }
}
