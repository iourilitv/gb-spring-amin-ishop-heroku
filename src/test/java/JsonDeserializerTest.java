import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.deserializers.*;

public class JsonDeserializerTest {
    public static void main(String[] args) {
        Gson gsonOutEntity = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
                .create();

        String json = "{\n" +
                "  \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "  \"entityType\": \"Event\",\n" +
                "  \"entityFields\": {\n" +
                "    \"actionType\": {\n" +
                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "      \"entityType\": \"ActionType\",\n" +
                "      \"entityFields\": {\n" +
                "        \"entityType\": \"Order\",\n" +
                "        \"description\": \"Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"CREATED\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"recipientAcceptedAt\": \"2020-09-04T06:24:33\",\n" +
                "    \"issuerEventId\": 0,\n" +
                "    \"entityType\": \"Order\",\n" +
                "    \"entityId\": 19,\n" +
                "    \"issuerCreatedAt\": \"2020-09-12T19:21:11\",\n" +
                "    \"id\": 1,\n" +
                "    \"issuer\": \"STORE\",\n" +
                "    \"entity\": {\n" +
                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "      \"entityType\": \"Order\",\n" +
                "      \"entityFields\": {\n" +
                "        \"delivery\": {\n" +
                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "          \"entityType\": \"Delivery\",\n" +
                "          \"entityFields\": {\n" +
                "            \"phoneNumber\": \"+79991234567\",\n" +
                "            \"deliveryAddress\": {\n" +
                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "              \"entityType\": \"Address\",\n" +
                "              \"entityFields\": {\n" +
                "                \"country\": \"Russia\",\n" +
                "                \"address\": \"Секина 99, кв.99\",\n" +
                "                \"city\": \"Королев МО\",\n" +
                "                \"id\": 3\n" +
                "              }\n" +
                "            },\n" +
                "            \"id\": 17,\n" +
                "            \"deliveryExpectedAt\": \"2020-09-12T10:00:00\",\n" +
                "            \"order\": 19,\n" +
                "            \"deliveryCost\": 100.00,\n" +
                "            \"deliveredAt\": null\n" +
                "          }\n" +
                "        },\n" +
                "        \"createdAt\": \"2020-09-12T19:21:10\",\n" +
                "        \"totalCosts\": 1004.00,\n" +
                "        \"orderStatus\": {\n" +
                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "          \"entityType\": \"OrderStatus\",\n" +
                "          \"entityFields\": {\n" +
                "            \"description\": \"Создан: Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
                "            \"id\": 1,\n" +
                "            \"title\": \"Created\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"totalItemsCosts\": 904.00,\n" +
                "        \"id\": 19,\n" +
                "        \"user\": {\n" +
                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "          \"entityType\": \"User\",\n" +
                "          \"entityFields\": {\n" +
                "            \"firstName\": \"f n liyuse\",\n" +
                "            \"lastName\": \"l n liyuse\",\n" +
                "            \"phoneNumber\": \"+79991234567\",\n" +
                "            \"deliveryAddress\": {\n" +
                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "              \"entityType\": \"Address\",\n" +
                "              \"entityFields\": {\n" +
                "                \"country\": \"Russia\",\n" +
                "                \"address\": \"Секина 99, кв.99\",\n" +
                "                \"city\": \"Королев МО\",\n" +
                "                \"id\": 3\n" +
                "              }\n" +
                "            },\n" +
                "            \"id\": 2,\n" +
                "            \"userName\": \"liyuse\",\n" +
                "            \"email\": \"liyuse@yandex.ru\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"orderItems\": [\n" +
                "          {\n" +
                "            \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "            \"entityType\": \"OrderItem\",\n" +
                "            \"entityFields\": {\n" +
                "              \"itemCosts\": 904.00,\n" +
                "              \"product\": {\n" +
                "                \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "                \"entityType\": \"Product\",\n" +
                "                \"entityFields\": {\n" +
                "                  \"price\": 904.00,\n" +
                "                  \"id\": 10,\n" +
                "                  \"shortDescription\": \"Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик \\\"Зверье Мое\\\" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.\",\n" +
                "                  \"category\": {\n" +
                "                    \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "                    \"entityType\": \"Category\",\n" +
                "                    \"entityFields\": {\n" +
                "                      \"id\": 7,\n" +
                "                      \"title\": \"Pets\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"title\": \"Зверье Моё / Когтеточка-столбик \\\"Зверьё Моё\\\" с полкой, джут, крем-брюле, 40*40*60 см\",\n" +
                "                  \"vendorCode\": \"00000010\"\n" +
                "                }\n" +
                "              },\n" +
                "              \"quantity\": 1,\n" +
                "              \"itemPrice\": 904.00,\n" +
                "              \"id\": 27,\n" +
                "              \"order\": 19\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"updatedAt\": \"2020-09-12T19:21:11\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String json2 = "{\n" +
                "        \"entityType\": \"Order\",\n" +
                "        \"description\": \"Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"CREATED\"\n" +
                "      }";

        String json3 = "{\n" +
                "  \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "  \"entityType\": \"Event\",\n" +
                "  \"entityFields\": {\n" +
                "    \"actionType\": {\n" +
                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "      \"entityType\": \"ActionType\",\n" +
                "      \"entityFields\": {\n" +
                "        \"entityType\": \"Order\",\n" +
                "        \"description\": \"Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"CREATED\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"recipientAcceptedAt\": \"2020-09-04T06:24:33\",\n" +
                "    \"issuerEventId\": 0,\n" +
                "    \"entityType\": \"Order\",\n" +
                "    \"entityId\": 19,\n" +
                "    \"issuerCreatedAt\": \"2020-09-12T19:21:11\",\n" +
                "    \"id\": 1,\n" +
                "    \"issuer\": \"STORE\"\n" +
                "  }\n" +
                "}";

//        OutEntity outEntity = gsonOutEntity.fromJson(json, OutEntity.class);
//        OutEntity outEntity = gsonOutEntity.fromJson(json2, OutEntity.class);
        OutEntity outEntity = gsonOutEntity.fromJson(json3, OutEntity.class);
        System.out.println("*** RESULT: " + outEntity);
        //OutEntity(store=gb-spring-amin-ishop-heroku, entityType=Event, entityFields={actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0, issuer=STORE, entity={store=gb-spring-amin-ishop-heroku, entityType=Order, entityFields={delivery={store=gb-spring-amin-ishop-heroku, entityType=Delivery, entityFields={phoneNumber=+79991234567, deliveryAddress={store=gb-spring-amin-ishop-heroku, entityType=Address, entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3.0}}, id=17.0, deliveryExpectedAt=2020-09-12T10:00:00, order=19.0, deliveryCost=100.0, deliveredAt=null}}, createdAt=2020-09-12T19:21:10, totalCosts=1004.0, orderStatus={store=gb-spring-amin-ishop-heroku, entityType=OrderStatus, entityFields={description=Создан: Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=Created}}, totalItemsCosts=904.0, id=19.0, user={store=gb-spring-amin-ishop-heroku, entityType=User, entityFields={firstName=f n liyuse, lastName=l n liyuse, phoneNumber=+79991234567, deliveryAddress={store=gb-spring-amin-ishop-heroku, entityType=Address, entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3.0}}, id=2.0, userName=liyuse, email=liyuse@yandex.ru}}, orderItems=[{store=gb-spring-amin-ishop-heroku, entityType=OrderItem, entityFields={itemCosts=904.0, product={store=gb-spring-amin-ishop-heroku, entityType=Product, entityFields={price=904.0, id=10.0, shortDescription=Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик "Зверье Мое" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела., category={store=gb-spring-amin-ishop-heroku, entityType=Category, entityFields={id=7.0, title=Pets}}, title=Зверье Моё / Когтеточка-столбик "Зверьё Моё" с полкой, джут, крем-брюле, 40*40*60 см, vendorCode=00000010}}, quantity=1.0, itemPrice=904.0, id=27.0, order=19.0}}], updatedAt=2020-09-12T19:21:11}}})

//        Map<String,Object> mapOut = (Map<String,Object>) outEntity.getEntityFields().get("actionType");
//        Map<String,Object> map = (Map<String,Object>) mapOut.get("entityFields");
//        System.out.println("** map: " + map);
//        //** map: {entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}
//
//        System.out.println("** id: " + map.get("id"));
//        System.out.println("** title: " + map.get("title"));
//        System.out.println("** description: " + map.get("description"));
//        System.out.println("** entityType: " + map.get("entityType"));
//        //** id: 1.0
//        //** title: CREATED
//        //** description: Заказ сформирован пользователем и сохранен в списке заказов
//        //** entityType: Order

//        outEntity = gsonOutEntity.fromJson((JsonElement) outEntity.getEntityFields().get("actionType"), OutEntity.class);
        //Exception in thread "main" java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.google.gson.JsonElement

//        System.out.println(outEntity.getEntityFields().get(Event.Fields.entity.name()));
        IEventBuilder eventBuilder = new EventBuilder();
//        Event event = eventBuilder.create(outEntity.getEntityFields());
        Event event = eventBuilder.create(outEntity);

        System.out.println(event);
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): actionType
        //OutEntityDeserializer.deserialize().jE.getValue(): {"store":"gb-spring-amin-ishop-heroku","entityType":"ActionType","entityFields":{"entityType":"Order","description":"Заказ сформирован пользователем и сохранен в списке заказов","id":1,"title":"CREATED"}}
        //OutEntityDeserializer.deserialize().objectInternal: {store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): recipientAcceptedAt
        //OutEntityDeserializer.deserialize().jE.getValue(): "2020-09-04T06:24:33"
        //OutEntityDeserializer.deserialize().objectInternal: 2020-09-04T06:24:33
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): issuerEventId
        //OutEntityDeserializer.deserialize().jE.getValue(): 0
        //OutEntityDeserializer.deserialize().objectInternal: 0.0
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): entityType
        //OutEntityDeserializer.deserialize().jE.getValue(): "Order"
        //OutEntityDeserializer.deserialize().objectInternal: Order
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): entityId
        //OutEntityDeserializer.deserialize().jE.getValue(): 19
        //OutEntityDeserializer.deserialize().objectInternal: 19.0
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): issuerCreatedAt
        //OutEntityDeserializer.deserialize().jE.getValue(): "2020-09-12T19:21:11"
        //OutEntityDeserializer.deserialize().objectInternal: 2020-09-12T19:21:11
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): id
        //OutEntityDeserializer.deserialize().jE.getValue(): 1
        //OutEntityDeserializer.deserialize().objectInternal: 1.0
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0}
        //OutEntityDeserializer.deserialize().OutEntity.Fields.entityFields.name()): entityFields
        //OutEntityDeserializer.deserialize().jE.getKey(): issuer
        //OutEntityDeserializer.deserialize().jE.getValue(): "STORE"
        //OutEntityDeserializer.deserialize().objectInternal: STORE
        //OutEntityDeserializer.deserialize().Map.entityFields: {actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0, issuer=STORE}
        //outEntity: OutEntity{store='gb-spring-amin-ishop-heroku', entityType='Event', entityFields={actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0, issuer=STORE}}
        //*** RESULT: OutEntity{store='gb-spring-amin-ishop-heroku', entityType='Event', entityFields={actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0, issuer=STORE}}
        //EventBuilder.create().incoming outEntity: OutEntity{store='gb-spring-amin-ishop-heroku', entityType='Event', entityFields={actionType={store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}, recipientAcceptedAt=2020-09-04T06:24:33, issuerEventId=0.0, entityType=Order, entityId=19.0, issuerCreatedAt=2020-09-12T19:21:11, id=1.0, issuer=STORE}}
        //treeMap: {store=gb-spring-amin-ishop-heroku, entityType=ActionType, entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}
        //ActionTypeBuilder.create().incoming outEntity: OutEntity{store='gb-spring-amin-ishop-heroku', entityType='ActionType', entityFields={entityType=Order, description=Заказ сформирован пользователем и сохранен в списке заказов, id=1.0, title=CREATED}}
        //Event{id=1, actionType=ActionType{id=1, title='CREATED', description='Заказ сформирован пользователем и сохранен в списке заказов', entityType='Order'}, issuer='STORE', issuerEventId=0, entityType='Order', entityId=19, issuerCreatedAt=2020-09-12T19:21:11, recipientAcceptedAt=2020-09-04T06:24:33}
    }
}

//        String json = "{\n" +
//                "  \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "  \"entityClassSimpleName\": \"Event\",\n" +
//                "  \"body\": {\n" +
//                "    \"Order\": {\n" +
//                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "      \"entityClassSimpleName\": \"Order\",\n" +
//                "      \"body\": {\n" +
//                "        \"delivery\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"Delivery\",\n" +
//                "          \"body\": {\n" +
//                "            \"phoneNumber\": \"+79991234567\",\n" +
//                "            \"deliveryAddress\": {\n" +
//                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "              \"entityClassSimpleName\": \"Address\",\n" +
//                "              \"body\": {\n" +
//                "                \"country\": \"Russia\",\n" +
//                "                \"address\": \"Секина 99, кв.99\",\n" +
//                "                \"city\": \"Королев МО\",\n" +
//                "                \"id\": 3\n" +
//                "              }\n" +
//                "            },\n" +
//                "            \"id\": 17,\n" +
//                "            \"deliveryExpectedAt\": \"2020-09-12T10:00:00\",\n" +
//                "            \"order\": 19,\n" +
//                "            \"deliveryCost\": 100.00,\n" +
//                "            \"deliveredAt\": null\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"createdAt\": \"2020-09-12T19:21:10\",\n" +
//                "        \"totalCosts\": 1004.00,\n" +
//                "        \"orderStatus\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"OrderStatus\",\n" +
//                "          \"body\": {\n" +
//                "            \"description\": \"Создан: Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
//                "            \"id\": 1,\n" +
//                "            \"title\": \"Created\"\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"totalItemsCosts\": 904.00,\n" +
//                "        \"id\": 19,\n" +
//                "        \"user\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"User\",\n" +
//                "          \"body\": {\n" +
//                "            \"firstName\": \"f n liyuse\",\n" +
//                "            \"lastName\": \"l n liyuse\",\n" +
//                "            \"phoneNumber\": \"+79991234567\",\n" +
//                "            \"deliveryAddress\": {\n" +
//                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "              \"entityClassSimpleName\": \"Address\",\n" +
//                "              \"body\": {\n" +
//                "                \"country\": \"Russia\",\n" +
//                "                \"address\": \"Секина 99, кв.99\",\n" +
//                "                \"city\": \"Королев МО\",\n" +
//                "                \"id\": 3\n" +
//                "              }\n" +
//                "            },\n" +
//                "            \"id\": 2,\n" +
//                "            \"userName\": \"liyuse\",\n" +
//                "            \"email\": \"liyuse@yandex.ru\"\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"orderItems\": [\n" +
//                "          {\n" +
//                "            \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "            \"entityClassSimpleName\": \"OrderItem\",\n" +
//                "            \"body\": {\n" +
//                "              \"itemCosts\": 904.00,\n" +
//                "              \"product\": {\n" +
//                "                \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "                \"entityClassSimpleName\": \"Product\",\n" +
//                "                \"body\": {\n" +
//                "                  \"price\": 904.00,\n" +
//                "                  \"id\": 10,\n" +
//                "                  \"shortDescription\": \"Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик \\\"Зверье Мое\\\" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.\",\n" +
//                "                  \"category\": {\n" +
//                "                    \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "                    \"entityClassSimpleName\": \"Category\",\n" +
//                "                    \"body\": {\n" +
//                "                      \"id\": 7,\n" +
//                "                      \"title\": \"Pets\"\n" +
//                "                    }\n" +
//                "                  },\n" +
//                "                  \"title\": \"Зверье Моё / Когтеточка-столбик \\\"Зверьё Моё\\\" с полкой, джут, крем-брюле, 40*40*60 см\",\n" +
//                "                  \"vendorCode\": \"00000010\"\n" +
//                "                }\n" +
//                "              },\n" +
//                "              \"quantity\": 1,\n" +
//                "              \"itemPrice\": 904.00,\n" +
//                "              \"id\": 27,\n" +
//                "              \"order\": 19\n" +
//                "            }\n" +
//                "          }\n" +
//                "        ],\n" +
//                "        \"updatedAt\": \"2020-09-12T19:21:11\"\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"actionType\": {\n" +
//                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "      \"entityClassSimpleName\": \"ActionType\",\n" +
//                "      \"body\": {\n" +
//                "        \"entityType\": \"Order\",\n" +
//                "        \"description\": \"Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
//                "        \"id\": 1,\n" +
//                "        \"title\": \"CREATED\"\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"recipientAcceptedAt\": \"2020-09-04T06:24:33\",\n" +
//                "    \"issuerEventId\": 0,\n" +
//                "    \"entityType\": \"Order\",\n" +
//                "    \"entityId\": 19,\n" +
//                "    \"issuerCreatedAt\": \"2020-09-12T19:21:11\",\n" +
//                "    \"id\": 1,\n" +
//                "    \"issuer\": \"STORE\"\n" +
//                "  }\n" +
//                "}";

//        String json = "{\n" +
//                "  \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "  \"entityClassSimpleName\": \"Event\",\n" +
//                "  \"body\": {\n" +
//                "    \"actionType\": {\n" +
//                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "      \"entityClassSimpleName\": \"ActionType\",\n" +
//                "      \"body\": {\n" +
//                "        \"entityType\": \"Order\",\n" +
//                "        \"description\": \"Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
//                "        \"id\": 1,\n" +
//                "        \"title\": \"CREATED\"\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"recipientAcceptedAt\": \"2020-09-04T06:24:33\",\n" +
//                "    \"issuerEventId\": 0,\n" +
//                "    \"entityType\": \"Order\",\n" +
//                "    \"entityId\": 19,\n" +
//                "    \"issuerCreatedAt\": \"2020-09-12T19:21:11\",\n" +
//                "    \"id\": 1,\n" +
//                "    \"issuer\": \"STORE\",\n" +
//                "    \"entity\": {\n" +
//                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "      \"entityClassSimpleName\": \"Order\",\n" +
//                "      \"body\": {\n" +
//                "        \"delivery\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"Delivery\",\n" +
//                "          \"body\": {\n" +
//                "            \"phoneNumber\": \"+79991234567\",\n" +
//                "            \"deliveryAddress\": {\n" +
//                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "              \"entityClassSimpleName\": \"Address\",\n" +
//                "              \"body\": {\n" +
//                "                \"country\": \"Russia\",\n" +
//                "                \"address\": \"Секина 99, кв.99\",\n" +
//                "                \"city\": \"Королев МО\",\n" +
//                "                \"id\": 3\n" +
//                "              }\n" +
//                "            },\n" +
//                "            \"id\": 17,\n" +
//                "            \"deliveryExpectedAt\": \"2020-09-12T10:00:00\",\n" +
//                "            \"order\": 19,\n" +
//                "            \"deliveryCost\": 100.00,\n" +
//                "            \"deliveredAt\": null\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"createdAt\": \"2020-09-12T19:21:10\",\n" +
//                "        \"totalCosts\": 1004.00,\n" +
//                "        \"orderStatus\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"OrderStatus\",\n" +
//                "          \"body\": {\n" +
//                "            \"description\": \"Создан: Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
//                "            \"id\": 1,\n" +
//                "            \"title\": \"Created\"\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"totalItemsCosts\": 904.00,\n" +
//                "        \"id\": 19,\n" +
//                "        \"user\": {\n" +
//                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "          \"entityClassSimpleName\": \"User\",\n" +
//                "          \"body\": {\n" +
//                "            \"firstName\": \"f n liyuse\",\n" +
//                "            \"lastName\": \"l n liyuse\",\n" +
//                "            \"phoneNumber\": \"+79991234567\",\n" +
//                "            \"deliveryAddress\": {\n" +
//                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "              \"entityClassSimpleName\": \"Address\",\n" +
//                "              \"body\": {\n" +
//                "                \"country\": \"Russia\",\n" +
//                "                \"address\": \"Секина 99, кв.99\",\n" +
//                "                \"city\": \"Королев МО\",\n" +
//                "                \"id\": 3\n" +
//                "              }\n" +
//                "            },\n" +
//                "            \"id\": 2,\n" +
//                "            \"userName\": \"liyuse\",\n" +
//                "            \"email\": \"liyuse@yandex.ru\"\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"orderItems\": [\n" +
//                "          {\n" +
//                "            \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "            \"entityClassSimpleName\": \"OrderItem\",\n" +
//                "            \"body\": {\n" +
//                "              \"itemCosts\": 904.00,\n" +
//                "              \"product\": {\n" +
//                "                \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "                \"entityClassSimpleName\": \"Product\",\n" +
//                "                \"body\": {\n" +
//                "                  \"price\": 904.00,\n" +
//                "                  \"id\": 10,\n" +
//                "                  \"shortDescription\": \"Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик \\\"Зверье Мое\\\" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.\",\n" +
//                "                  \"category\": {\n" +
//                "                    \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
//                "                    \"entityClassSimpleName\": \"Category\",\n" +
//                "                    \"body\": {\n" +
//                "                      \"id\": 7,\n" +
//                "                      \"title\": \"Pets\"\n" +
//                "                    }\n" +
//                "                  },\n" +
//                "                  \"title\": \"Зверье Моё / Когтеточка-столбик \\\"Зверьё Моё\\\" с полкой, джут, крем-брюле, 40*40*60 см\",\n" +
//                "                  \"vendorCode\": \"00000010\"\n" +
//                "                }\n" +
//                "              },\n" +
//                "              \"quantity\": 1,\n" +
//                "              \"itemPrice\": 904.00,\n" +
//                "              \"id\": 27,\n" +
//                "              \"order\": 19\n" +
//                "            }\n" +
//                "          }\n" +
//                "        ],\n" +
//                "        \"updatedAt\": \"2020-09-12T19:21:11\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";