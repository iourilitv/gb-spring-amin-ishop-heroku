package ru.geekbrains.spring.ishop.rest.converters.serializers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.rest.converters.serializers.interfaces.IEntitySerializer;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductSerializer implements IEntitySerializer {
    private final OutEntitySerializer outEntitySerializer;

    @Override
    public void fillEntityFields(AbstractEntity entity, Map<String, Object> entityFields) {
        Product product = (Product) entity;
        entityFields.put(Product.Fields.id.name(), product.getId());
        entityFields.put(Product.Fields.category.name(), outEntitySerializer.convertEntityToOutEntity(product.getCategory()));
        entityFields.put(Product.Fields.vendorCode.name(), product.getVendorCode());
        entityFields.put(Product.Fields.title.name(), product.getTitle());
        entityFields.put(Product.Fields.shortDescription.name(), product.getPrice());
        entityFields.put(Product.Fields.fullDescription.name(), product.getShortDescription());
    }

}
