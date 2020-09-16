package ru.geekbrains.spring.ishop.rest.deserializers;

import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

import java.util.Map;

@Component
public class ActionTypeBuilder implements IEntityBuilder<ActionType> {
    @Override
    public ActionType create(OutEntity outEntity) {

        System.out.println("ActionTypeBuilder.create().incoming outEntity: " + outEntity);

        Map<String,Object> fields = outEntity.getEntityFields();
        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
        return ActionType.builder()
                .id((new Double(doubleId)).shortValue())
                .title(String.valueOf(outEntity.getEntityFields().get("title")))
                .description(String.valueOf(fields.get("description")))
                .entityType(String.valueOf(fields.get("entityType")))
                .build();
    }
//    @Override
//    public ActionType create(Map<String, Object> fields) {
//        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
//        return ActionType.builder()
//                .id((new Double(doubleId)).shortValue())
//                .title(String.valueOf(fields.get("title")))
//                .description(String.valueOf(fields.get("description")))
//                .entityType(String.valueOf(fields.get("entityType")))
//                .build();
//    }

}
