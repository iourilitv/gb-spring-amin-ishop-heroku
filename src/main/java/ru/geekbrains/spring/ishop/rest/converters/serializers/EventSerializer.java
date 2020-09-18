package ru.geekbrains.spring.ishop.rest.converters.serializers;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

import java.util.Map;

@Service
public class EventSerializer implements IEntitySerializer {
    private OutEntityService outEntityService;

    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Event event = (Event) entity;
        entityFields.put(Event.Fields.id.name(), event.getId());
        entityFields.put(Event.Fields.actionType.name(), outEntityService.convertEntityToOutEntity(event.getActionType()));
        entityFields.put(Event.Fields.entityType.name(), event.getEntityType());
        entityFields.put(Event.Fields.entityId.name(), event.getEntityId());
        entityFields.put(Event.Fields.createdAt.name(), event.getCreatedAt());
        entityFields.put(Event.Fields.serverAcceptedAt.name(), event.getServerAcceptedAt());
    }
}
