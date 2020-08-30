package ru.geekbrains.spring.ishop.rest.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.rest.outentities.OutProduct;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

@Service
public class ProductToOutProductConverter implements Converter<Product, OutProduct> {

    private OutEntityService outEntityService;

    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Override
    public OutProduct convert(@Nullable Product product) {
        assert product != null;
        return outEntityService.createOutProduct(product);
    }

}
