import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.deserializers.OutEntityDeserializer;

public class JsonDeserializerTest {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OutEntity.class, new OutEntityDeserializer())
                .create();

        String json = "{\n" +
                "  \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "  \"entityClassSimpleName\": \"Event\",\n" +
                "  \"body\": {\n" +
                "    \"actionType\": {\n" +
                "      \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "      \"entityClassSimpleName\": \"ActionType\",\n" +
                "      \"body\": {\n" +
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
                "      \"entityClassSimpleName\": \"Order\",\n" +
                "      \"body\": {\n" +
                "        \"delivery\": {\n" +
                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "          \"entityClassSimpleName\": \"Delivery\",\n" +
                "          \"body\": {\n" +
                "            \"phoneNumber\": \"+79991234567\",\n" +
                "            \"deliveryAddress\": {\n" +
                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "              \"entityClassSimpleName\": \"Address\",\n" +
                "              \"body\": {\n" +
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
                "          \"entityClassSimpleName\": \"OrderStatus\",\n" +
                "          \"body\": {\n" +
                "            \"description\": \"Создан: Заказ сформирован пользователем и сохранен в списке заказов\",\n" +
                "            \"id\": 1,\n" +
                "            \"title\": \"Created\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"totalItemsCosts\": 904.00,\n" +
                "        \"id\": 19,\n" +
                "        \"user\": {\n" +
                "          \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "          \"entityClassSimpleName\": \"User\",\n" +
                "          \"body\": {\n" +
                "            \"firstName\": \"f n liyuse\",\n" +
                "            \"lastName\": \"l n liyuse\",\n" +
                "            \"phoneNumber\": \"+79991234567\",\n" +
                "            \"deliveryAddress\": {\n" +
                "              \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "              \"entityClassSimpleName\": \"Address\",\n" +
                "              \"body\": {\n" +
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
                "            \"entityClassSimpleName\": \"OrderItem\",\n" +
                "            \"body\": {\n" +
                "              \"itemCosts\": 904.00,\n" +
                "              \"product\": {\n" +
                "                \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "                \"entityClassSimpleName\": \"Product\",\n" +
                "                \"body\": {\n" +
                "                  \"price\": 904.00,\n" +
                "                  \"id\": 10,\n" +
                "                  \"shortDescription\": \"Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик \\\"Зверье Мое\\\" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.\",\n" +
                "                  \"category\": {\n" +
                "                    \"store\": \"gb-spring-amin-ishop-heroku\",\n" +
                "                    \"entityClassSimpleName\": \"Category\",\n" +
                "                    \"body\": {\n" +
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

        OutEntity outEntity = gson.fromJson(json, OutEntity.class);
        System.out.println(outEntity);

        Event event = Event.builder()

                .build();
        System.out.println(event);
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