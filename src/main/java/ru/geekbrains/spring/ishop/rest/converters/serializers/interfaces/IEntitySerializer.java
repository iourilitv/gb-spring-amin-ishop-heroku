package ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;

import java.util.Map;

@Service
public interface IEntitySerializer {
    void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields);
}
