package ru.geekbrains.spring.ishop;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.AddressDeserializerImpl;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.AddressSerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.OutEntitySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sources.
 * 1. https://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/reference/html/boot-features-testing.html
 *    (41.3.6 Auto-configured JSON tests).
 * 2. https://www.baeldung.com/introduction-to-assertj
 */

@JsonTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class AddressJsonTest {

    private @Autowired JacksonTester<OutEntity> json;
    private OutEntitySerializer outEntitySerializer;
    private OutEntityDeserializer outEntityDeserializer;

    @PostConstruct
    public void init() {
        outEntitySerializer = new OutEntitySerializer();
        Map<String, IEntitySerializer> serializers = new HashMap<>();
        serializers.put(EntityTypes.Address.name(), new AddressSerializer());
        SerializerFactory serializerFactory = new SerializerFactory();
        serializerFactory.initSerializerFactory(serializers);
        outEntitySerializer.setSerializerFactory(serializerFactory);

        outEntityDeserializer = new OutEntityDeserializer();
        Map<String, IEntityDeserializer> deserializers = new HashMap<>();
        deserializers.put(EntityTypes.Address.name(), new AddressDeserializerImpl());
        DeserializerFactory deserializerFactory = new DeserializerFactory();
        deserializerFactory.initDeserializerFactory(deserializers);
        outEntityDeserializer.setDeserializerFactory(deserializerFactory);
    }

    @Test
    public void test1_givenOutEntityJson_whenRecognizeToEntity_thenReturnEntityDeserialized() {
        Address expected = createTestAddress();
        OutEntity outEntity = outEntitySerializer.convertEntityToOutEntity(expected);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(outEntity, OutEntity.class);
        Address actual = (Address) outEntityDeserializer.deserializeEntityFromOutEntityJson(outEntity.getEntityType(), jsonElement);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test2_givenAddress_whenConvertEntityToOutEntity_thenReturnOutEntitySerializedToJson() throws IOException {
        Address address = createTestAddress();
        OutEntity outEntity = outEntitySerializer.convertEntityToOutEntity(address);
        JsonContent<OutEntity> expected = json.write(outEntity);
        assertThat(expected).hasJsonPathStringValue("@.entityType");
        assertThat(expected).extractingJsonPathStringValue("@.entityType")
                .isEqualTo("Address");
        assertThat(expected).isEqualToJson("json/Address.json");
    }

    private @NonNull Address createTestAddress() {
        return new Address(3L, "Russia", "Королев МО", "Секина 99, кв.99");
    }
}
