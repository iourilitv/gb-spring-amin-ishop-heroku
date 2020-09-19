package ru.geekbrains.spring.ishop.rest.converters.serializers;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
public class CategorySerializer implements IEntitySerializer {
    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Category category = (Category) entity;
        entityFields.put(Category.Fields.id.name(), category.getId());
        entityFields.put(Category.Fields.title.name(), category.getTitle());
    }
}
