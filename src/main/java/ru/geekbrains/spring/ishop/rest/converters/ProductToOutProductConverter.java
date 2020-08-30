package ru.geekbrains.spring.ishop.rest.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.rest.outentities.OutProduct;

@Service
public class ProductToOutProductConverter implements Converter<Product, OutProduct> {
    @Override
    public OutProduct convert(Product product) {
        OutProduct out = new OutProduct();
        out.setId(product.getId());
        out.setCategoryTitle(product.getCategory().getTitle());
        out.setVendorCode(product.getVendorCode());
        out.setTitle(product.getTitle());
        out.setPrice(product.getPrice());
        out.setShortDescription(product.getShortDescription());
        out.setFullDescription(product.getFullDescription());
        out.setCreateAt(product.getCreateAt());
        out.setUpdateAt(product.getCreateAt());
        return out;
    }

}
