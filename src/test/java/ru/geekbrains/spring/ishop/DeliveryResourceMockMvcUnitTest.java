package ru.geekbrains.spring.ishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.OutEntitySerializeException;
import ru.geekbrains.spring.ishop.repository.RoleRepository;
import ru.geekbrains.spring.ishop.rest.converters.serializers.DeliverySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.resources.DeliveryResource;
import ru.geekbrains.spring.ishop.service.DeliveryService;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Slf4j
public class DeliveryResourceMockMvcUnitTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private DeliveryResource deliveryResource;
    @MockBean
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        deliveryResource = new DeliveryResource(deliveryService);
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryResource).alwaysDo(print()).build();
    }

    @Test
    public void givenId_whenGetExistingDelivery_thenStatus200andOutEntityReturned() throws Exception {
        Delivery delivery = createDelivery();
        OutEntity outEntity = convertEntityToOutEntity(delivery);
        Mockito.when(deliveryService.findByIdOptional(Mockito.any())).thenReturn(delivery);
        Mockito.when(deliveryService.convertDeliveryToOutEntity(Mockito.any())).thenReturn(outEntity);

        mockMvc.perform(
                get("/api/v1/order/delivery/1/deliveryId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
    }

    public OutEntity convertEntityToOutEntity(AbstractEntity entity) {
        Map<String, Object> entityFields = new HashMap<>();
        String entityType = "";

        if(entity instanceof Delivery) {
            entityType = EntityTypes.Delivery.name();
            fillDeliveryEntityFields(entity, entityFields);
        } else if( entity instanceof Address) {
            entityType = EntityTypes.Address.name();
            fillAddressEntityFields(entity, entityFields);
        }

        return OutEntity.builder()
                .entityType(entityType)
                .entityFields(entityFields)
                .build();
    }

    private void fillDeliveryEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Delivery delivery = (Delivery) entity;
        entityFields.put(Delivery.Fields.id.name(), delivery.getId());
        entityFields.put(Delivery.Fields.order.name(), delivery.getOrder().getId());
        entityFields.put(Delivery.Fields.phoneNumber.name(), delivery.getPhoneNumber());
        entityFields.put(Delivery.Fields.deliveryAddress.name(),
                convertEntityToOutEntity(delivery.getDeliveryAddress()).toJsonString());
//        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost());
        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost().toString());

//        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt());
        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt().toString());
        if(delivery.getDeliveredAt() != null) {
            entityFields.put(Delivery.Fields.deliveredAt.name(), delivery.getDeliveredAt().toString());
        }
    }

    private void fillAddressEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Address address = (Address) entity;
        entityFields.put(Address.Fields.id.name(), address.getId());
        entityFields.put(Address.Fields.country.name(), address.getCountry());
        entityFields.put(Address.Fields.city.name(), address.getCity());
        entityFields.put(Address.Fields.address.name(), address.getAddress());
    }

//    private Delivery createDelivery() {
//        return Delivery.builder()
//                .id(1L)
//                .order(createOrder())
//                .phoneNumber("+79991234567")
//                .deliveryAddress(createAddress())
//                .deliveryCost(new BigDecimal("100.00"))
//                .deliveryExpectedAt(LocalDateTime.parse("2020-09-05T10:00:00"))
//                .build();
//    }
    private Delivery createDelivery() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);
        delivery.setOrder(createOrder());
        delivery.setPhoneNumber("+79991234567");
        delivery.setDeliveryAddress(createAddress());
        delivery.setDeliveryCost(new BigDecimal("100.00"));
        delivery.setDeliveryExpectedAt(LocalDateTime.parse("2020-09-05T10:00:00"));
        return delivery;
    }

    private Order createOrder() {
        Order order = new Order();
        order.setId(2L);
        return order;
    }
//    private Order createOrder() {
//        return Order.builder().id(2L).build();
//    }

    private Address createAddress() {
        Address address = new Address();
        address.setId(3L);
        address.setCountry("Russia");
        address.setCity("Королев МО");
        address.setAddress("Секина 99, кв.99");
        return address;
    }
//    private Address createAddress() {
//        return Address.builder().id(3L).country("Russia").city("Королев МО").address("Секина 99, кв.99").build();
//    }

    @Test
    public void giveDeliveryServerAcceptedAt_whenUpdate_thenStatus200andUpdatedOutEntityReturns() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Delivery delivery = createDelivery();
        delivery.setDeliveredAt(now);
        OutEntity outEntity = convertEntityToOutEntity(delivery);
        Mockito.when(deliveryService.updateServerAcceptedAt(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(delivery);
        Mockito.when(deliveryService.convertDeliveryToOutEntity(Mockito.any())).thenReturn(outEntity);

        mockMvc.perform(
                put("/api/v1/order/delivery/1/deliveryId/deliveredAt/string")
                        .content(now.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
    }


}
//#{
//#  "store": "gb-spring-amin-ishop-heroku",
//#  "entityType": "Delivery",
//#  "entityFields": {
//#    "phoneNumber": "+79991234567",
//#    "deliveryAddress": {
//#      "store": "gb-spring-amin-ishop-heroku",
//#      "entityType": "Address",
//#      "entityFields": {
//#        "country": "Russia",
//#        "address": "Секина 99, кв.99",
//#        "city": "Королев МО",
//#        "id": 3
//#      }
//#    },
//#    "id": 1,
//#    "deliveryExpectedAt": "2020-09-05T10:00:00",
//#    "order": 2,
//#    "deliveryCost": 100.00,
//#    "deliveredAt": null
//#  }
//#}