package ru.geekbrains.spring.ishop.rest.deserializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.deserializers.interfaces.IActionTypeBuilder;
import ru.geekbrains.spring.ishop.rest.deserializers.interfaces.IEventBuilder;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventBuilder implements IEventBuilder {
    private final IActionTypeBuilder actionTypeBuilder;

    @Override
    public Event create(OutEntity outEntity) {
        if(!outEntity.getEntityType().equals(EntityTypes.Event.name())) {
            return null;
        }
        Map<String,Object> fields = outEntity.getEntityFields();
        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.issuerEventId.name())));
        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.entityId.name())));

        OutEntity outEntityActionType = (OutEntity) fields.get(Event.Fields.actionType.name());
        ActionType actionType = actionTypeBuilder.create(outEntityActionType);

        return Event.builder()
                .id((new Double(doubleId)).longValue())
                .actionType(actionType)
                .issuer((String) fields.get(Event.Fields.issuer.name()))
                .issuerEventId((new Double(doubleIssuerEventId)).longValue())
                .entityType(String.valueOf(fields.get(Event.Fields.entityType.name())))
                .entityId((new Double(doubleEntityId)).longValue())
                .issuerCreatedAt(getLocalDateTimeOrNullIfAbsent(fields.get(Event.Fields.issuerCreatedAt.name())))
                .recipientAcceptedAt(getLocalDateTimeOrNullIfAbsent(fields.get(Event.Fields.recipientAcceptedAt.name())))
                .build();
    }

    private LocalDateTime getLocalDateTimeOrNullIfAbsent(Object object) {
        LocalDateTime localDateTime = null;
        if(object != null) {
            localDateTime = LocalDateTime.parse(String.valueOf(object));
            LocalDateTime.parse(String.valueOf(object));
        }
        return localDateTime;
    }

}