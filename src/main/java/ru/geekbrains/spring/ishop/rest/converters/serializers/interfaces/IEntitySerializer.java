package ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.rest.converters.IEntityConverter;

import java.util.Map;

@Service
//public interface IEntitySerializer {
//    void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields);
//}
public interface IEntitySerializer extends IEntityConverter {
    void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields);
}
