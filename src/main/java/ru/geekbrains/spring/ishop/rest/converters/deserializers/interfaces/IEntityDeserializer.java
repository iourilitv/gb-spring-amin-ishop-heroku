package ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces;

import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.rest.converters.IEntityConverter;

@Service
//public interface IEntityDeserializer {
//    AbstractEntity recognize(JsonElement json);
//}
public interface IEntityDeserializer extends IEntityConverter {
    AbstractEntity recognize(JsonElement json);
}
