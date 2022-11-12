package ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;

@Service
public interface EntityDeserializer<E extends AbstractEntity> extends IEntityDeserializer {

}
