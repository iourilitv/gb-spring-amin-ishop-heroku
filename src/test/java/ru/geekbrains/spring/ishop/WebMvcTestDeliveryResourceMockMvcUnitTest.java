package ru.geekbrains.spring.ishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.spring.ishop.control.CatalogController;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.repository.ProductRepository;
import ru.geekbrains.spring.ishop.repository.RoleRepository;
import ru.geekbrains.spring.ishop.repository.UserRepository;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.AddressSerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.DeliverySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.OutEntitySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.resources.DeliveryResource;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.service.*;
import ru.geekbrains.spring.ishop.utils.EntityTypes;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Slf4j
public class WebMvcTestDeliveryResourceMockMvcUnitTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private DeliveryResource deliveryResource;
    @MockBean
    private DeliveryService deliveryService;
    @MockBean
    private AddressService addressService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    @MockBean
    private OutEntityService outEntityService;

    private OutEntityDeserializer outEntityDeserializer;
    private DeserializerFactory deserializerFactory;
    private OutEntitySerializer outEntitySerializer;
    private SerializerFactory serializerFactory;
    private ApplicationContext context;



    @MockBean
    private UtilFilter utilFilter;
    @MockBean
    private UserService userService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private ShoppingCartService cartService;
    @MockBean
    private CatalogController catalogController;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new JavaTimeModule());

        serializerFactory = new SerializerFactory();
        outEntitySerializer = new OutEntitySerializer();
        Map<String, IEntitySerializer> serializers = new HashMap<>();
        outEntitySerializer.setSerializerFactory(serializerFactory);
        serializers.put(EntityTypes.Delivery.name(), new DeliverySerializer(outEntitySerializer));
        serializers.put(EntityTypes.Address.name(), new AddressSerializer());
        serializerFactory.initSerializerFactory(serializers);

        outEntityService = new OutEntityService(outEntityDeserializer, deserializerFactory, outEntitySerializer,
                                    serializerFactory, context);

        deliveryResource = new DeliveryResource(deliveryService, outEntityService, addressService);
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryResource).alwaysDo(print()).build();
    }
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        objectMapper.registerModule(new JavaTimeModule());
//        deliveryResource = new DeliveryResource(deliveryService);
//        mockMvc = MockMvcBuilders.standaloneSetup(deliveryResource).alwaysDo(print()).build();
//    }

    @Test
    public void givenId_whenGetExistingDelivery_thenStatus200andOutEntityReturned() throws Exception {
        Delivery delivery = createDelivery();
//        OutEntity outEntity = convertEntityToOutEntity(delivery);
        OutEntity outEntity = outEntityService.convertEntityToOutEntity(delivery);

        log.info("*********** outEntity: " + outEntity);

        Mockito.when(deliveryService.findByIdOptional(Mockito.any())).thenReturn(delivery);
//        Mockito.when(outEntityService.convertEntityToOutEntity(Mockito.any())).thenReturn(outEntity);

        mockMvc.perform(
                get("/api/v1/order/delivery/1/deliveryId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
    }
//    @Test
//    public void givenId_whenGetExistingDelivery_thenStatus200andOutEntityReturned() throws Exception {
//        Delivery delivery = createDelivery();
//        OutEntity outEntity = convertEntityToOutEntity(delivery);
//        Mockito.when(deliveryService.findByIdOptional(Mockito.any())).thenReturn(delivery);
//        Mockito.when(deliveryService.convertDeliveryToOutEntity(Mockito.any())).thenReturn(outEntity);
//
//        mockMvc.perform(
//                get("/api/v1/order/delivery/1/deliveryId"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
//                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
//                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
//    }

    //TODO don't use!
    @Test
    public void giveDeliveryServerAcceptedAt_whenUpdate_thenStatus200andUpdatedOutEntityReturns() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Delivery delivery = createDelivery();
        delivery.setDeliveredAt(now);
        OutEntity outEntity = outEntityService.convertEntityToOutEntity(delivery);
        Mockito.when(deliveryService.updateServerAcceptedAt(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(delivery);
        Mockito.when(outEntityService.convertEntityToOutEntity(Mockito.any())).thenReturn(outEntity);

        mockMvc.perform(
                put("/api/v1/order/delivery/1/deliveryId/deliveredAt/string")
                        .content(now.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
    }
    //    @Test
//    public void giveDeliveryServerAcceptedAt_whenUpdate_thenStatus200andUpdatedOutEntityReturns() throws Exception {
//        LocalDateTime now = LocalDateTime.now();
//        Delivery delivery = createDelivery();
//        delivery.setDeliveredAt(now);
//        OutEntity outEntity = convertEntityToOutEntity(delivery);
//        Mockito.when(deliveryService.updateServerAcceptedAt(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(delivery);
//        Mockito.when(deliveryService.convertDeliveryToOutEntity(Mockito.any())).thenReturn(outEntity);
//
//        mockMvc.perform(
//                put("/api/v1/order/delivery/1/deliveryId/deliveredAt/string")
//                        .content(now.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.store").value(outEntity.getStore()))
//                .andExpect(jsonPath("$.entityType").value(outEntity.getEntityType()))
//                .andExpect(jsonPath("$.entityFields").value(outEntity.getEntityFields()));
//    }

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
                outEntityService.convertEntityToOutEntity(delivery.getDeliveryAddress()).toJsonString());
//        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost());
        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost().toString());

//        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt());
        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt().toString());
        if(delivery.getDeliveredAt() != null) {
            entityFields.put(Delivery.Fields.deliveredAt.name(), delivery.getDeliveredAt().toString());
        }
    }
//    private void fillDeliveryEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
//        Delivery delivery = (Delivery) entity;
//        entityFields.put(Delivery.Fields.id.name(), delivery.getId());
//        entityFields.put(Delivery.Fields.order.name(), delivery.getOrder().getId());
//        entityFields.put(Delivery.Fields.phoneNumber.name(), delivery.getPhoneNumber());
//        entityFields.put(Delivery.Fields.deliveryAddress.name(),
//                convertEntityToOutEntity(delivery.getDeliveryAddress()).toJsonString());
////        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost());
//        entityFields.put(Delivery.Fields.deliveryCost.name(), delivery.getDeliveryCost().toString());
//
////        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt());
//        entityFields.put(Delivery.Fields.deliveryExpectedAt.name(), delivery.getDeliveryExpectedAt().toString());
//        if(delivery.getDeliveredAt() != null) {
//            entityFields.put(Delivery.Fields.deliveredAt.name(), delivery.getDeliveredAt().toString());
//        }
//    }

    private void fillAddressEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Address address = (Address) entity;
        entityFields.put(Address.Fields.id.name(), address.getId());
        entityFields.put(Address.Fields.country.name(), address.getCountry());
        entityFields.put(Address.Fields.city.name(), address.getCity());
        entityFields.put(Address.Fields.address.name(), address.getAddress());
    }

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

//givenId_whenGetExistingDelivery_thenStatus200andOutEntityReturned()
//with OutEntity outEntity = outEntityService.convertEntityToOutEntity(delivery);
//BEFORE updating OutEntity and DeliverySerializer.
//java.lang.AssertionError: JSON path "$.entityFields"
//Expected :{phoneNumber=+79991234567, deliveryAddress=OutEntity{store='gb-spring-amin-ishop-heroku', entityType='Address', entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3}}, id=1, deliveryExpectedAt=2020-09-05T10:00, order=2, deliveryCost=100.00, deliveredAt=null}
//Actual   :{phoneNumber=+79991234567, deliveryAddress={store=gb-spring-amin-ishop-heroku, entityType=Address, entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3}}, id=1, deliveryExpectedAt=[2020,9,5,10,0], order=2, deliveryCost=100.0, deliveredAt=null}
//AFTER updating OutEntity and DeliverySerializer.
//java.lang.AssertionError: JSON path "$.entityFields" - contents are identical.
//Expected :{phoneNumber=+79991234567, deliveryAddress={store=gb-spring-amin-ishop-heroku, entityType=Address, entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3}}, id=1, deliveryExpectedAt=2020-09-05T10:00, order=2, deliveryCost=100.00, deliveredAt=null}
//Actual   :{phoneNumber=+79991234567, deliveryAddress={store=gb-spring-amin-ishop-heroku, entityType=Address, entityFields={country=Russia, address=Секина 99, кв.99, city=Королев МО, id=3}}, id=1, deliveryExpectedAt=2020-09-05T10:00, order=2, deliveryCost=100.00, deliveredAt=null}