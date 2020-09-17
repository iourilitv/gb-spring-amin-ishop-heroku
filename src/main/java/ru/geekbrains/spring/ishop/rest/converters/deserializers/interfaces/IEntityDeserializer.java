package ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces;

import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;

@Service
public interface IEntityDeserializer {
    AbstractEntity recognize(JsonElement json);
}
