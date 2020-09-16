package ru.geekbrains.spring.ishop.rest.deserializers;

import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.deserializers.interfaces.IActionTypeBuilder;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.util.Map;

@Component
public class ActionTypeBuilder implements IActionTypeBuilder {
    @Override
    public ActionType create(OutEntity outEntity) {
        Map<String,Object> fields = outEntity.getEntityFields();
        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
        return ActionType.builder()
                .id((new Double(doubleId)).shortValue())
                .title(String.valueOf(outEntity.getEntityFields().get(ActionType.Fields.title.name())))
                .description(String.valueOf(fields.get(ActionType.Fields.description.name())))
                .entityType(String.valueOf(fields.get(ActionType.Fields.entityType.name())))
                .build();
    }

}
