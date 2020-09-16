package ru.geekbrains.spring.ishop.rest.deserializers;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Component
//@RequiredArgsConstructor
public class EventBuilder implements IEventBuilder {
//    private final OutEntityDeserializer outEntityDeserializer;
//    Gson gsonOutEntity = new GsonBuilder()
//        .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
//        .create();

//    @Override
//    public Event create(Map<String,Object> fields) {
//        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
//        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.issuerEventId.name())));
//        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.entityId.name())));
//
//        Map<String,Object> outEntityMap = (Map<String,Object>) fields.get(Event.Fields.actionType.name());
//        Map<String,Object> actionTypeMap = (Map<String,Object>) outEntityMap.get("entityFields");
//        System.out.println("** map: " + actionTypeMap);
//        //** map: {entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}
//
//
//        System.out.println("** id: " + actionTypeMap.get("id"));
//        System.out.println("** title: " + actionTypeMap.get("title"));
//        System.out.println("** description: " + actionTypeMap.get("description"));
//        System.out.println("** entityType: " + actionTypeMap.get("entityType"));
//        //** id: 1.0
//        //** title: CREATED
//        //** description: Заказ сформирован пользователем и сохранен в списке заказов
//        //** entityType: Order
//
//        IEntityBuilder actionTypeBuilder = new ActionTypeBuilder();
//        ActionType actionType = (ActionType) actionTypeBuilder.create(actionTypeMap);
//
//
//        return Event.builder()
//                .id((new Double(doubleId)).longValue())
//                .actionType(actionType)
//                .issuer((String) fields.get(Event.Fields.issuer.name()))
//                .issuerEventId((new Double(doubleIssuerEventId)).longValue())
//                .entityType(String.valueOf(fields.get(Event.Fields.entityType.name())))
//                .entityId((new Double(doubleEntityId)).longValue())
//                .issuerCreatedAt(LocalDateTime.parse(String.valueOf(fields.get(Event.Fields.issuerCreatedAt.name()))))
//                .recipientAcceptedAt(LocalDateTime.parse(String.valueOf(fields.get(Event.Fields.recipientAcceptedAt.name()))))
//                .build();
//    }
    @Override
    public Event create(OutEntity outEntity) {

        System.out.println("EventBuilder.create().incoming outEntity: " + outEntity);

        if(!outEntity.getEntityType().equals(EntityTypes.Event.name())) {
            return null;
        }
        Map<String,Object> fields = outEntity.getEntityFields();
        double doubleId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.id.name())));
        double doubleIssuerEventId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.issuerEventId.name())));
        double doubleEntityId = Double.parseDouble(String.valueOf(fields.get(Event.Fields.entityId.name())));


//        System.out.println("** id: " + actionTypeMap.get("id"));
//        System.out.println("** title: " + actionTypeMap.get("title"));
//        System.out.println("** description: " + actionTypeMap.get("description"));
//        System.out.println("** entityType: " + actionTypeMap.get("entityType"));
        //** id: 1.0
        //** title: CREATED
        //** description: Заказ сформирован пользователем и сохранен в списке заказов
        //** entityType: Order

//        OutEntity outEntityActionType = (OutEntity) fields.get(Event.Fields.actionType.name());
        LinkedTreeMap<String, Object> treeMap = (LinkedTreeMap<String, Object>) fields.get(Event.Fields.actionType.name());

        System.out.println("treeMap: " + treeMap);

        OutEntity outEntityActionType = OutEntity.builder()
                .entityType((String) treeMap.get(OutEntity.Fields.entityType.name()))
                .entityFields((LinkedTreeMap<String, Object>)treeMap.get(OutEntity.Fields.entityFields.name()))
                .build();
                IEntityBuilder actionTypeBuilder = new ActionTypeBuilder();
        ActionType actionType = (ActionType) actionTypeBuilder.create(outEntityActionType);


        return Event.builder()
                .id((new Double(doubleId)).longValue())
                .actionType(actionType)
                .issuer((String) fields.get(Event.Fields.issuer.name()))
                .issuerEventId((new Double(doubleIssuerEventId)).longValue())
                .entityType(String.valueOf(fields.get(Event.Fields.entityType.name())))
                .entityId((new Double(doubleEntityId)).longValue())
                .issuerCreatedAt(LocalDateTime.parse(String.valueOf(fields.get(Event.Fields.issuerCreatedAt.name()))))
                .recipientAcceptedAt(LocalDateTime.parse(String.valueOf(fields.get(Event.Fields.recipientAcceptedAt.name()))))
                .build();
    }

}