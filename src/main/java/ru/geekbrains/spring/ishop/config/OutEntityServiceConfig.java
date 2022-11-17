package ru.geekbrains.spring.ishop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.spring.ishop.rest.converters.DeserializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.SerializerFactory;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.OutEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces.IEntityDeserializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.OutEntitySerializer;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OutEntityServiceConfig {

    private @Autowired ApplicationContext context;

    @Bean
    public OutEntityDeserializer outEntityDeserializer() {
        Map<String, IEntityDeserializer> deserializers = extracted(IEntityDeserializer.class, OutEntityService.Converters.deserializer.name());
        DeserializerFactory deserializerFactory = new DeserializerFactory(deserializers);
        return new OutEntityDeserializer(deserializerFactory);
    }

    @Bean
    public OutEntitySerializer outEntitySerializer() {
        Map<String, IEntitySerializer> serializers = extracted(IEntitySerializer.class, OutEntityService.Converters.serializer.name());
        SerializerFactory serializerFactory = new SerializerFactory(serializers);
        return new OutEntitySerializer(serializerFactory);
    }

    private <I> Map<String, I> extracted(Class<I> iClass, String converterType) {
        Map<String, I> beanMap = context.getBeansOfType(iClass);
        Map<String, I> outMap = new HashMap<>(beanMap.size());
        for (String key : beanMap.keySet()) {
            for (EntityTypes type : EntityTypes.values()) {
                String modifiedKey = key.toLowerCase().replace(converterType, "");
                if(modifiedKey.equals(type.name().toLowerCase())) {
                    outMap.put(type.name(), beanMap.get(key));
                    break;
                }
            }
        }
        return outMap;
    }
}
