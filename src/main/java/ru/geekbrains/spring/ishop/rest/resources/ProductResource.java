package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.rest.converters.ProductToOutProductConverter;
import ru.geekbrains.spring.ishop.rest.outentities.OutProduct;
import ru.geekbrains.spring.ishop.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource extends AbstractResource {

    private ProductService productService;

    private ProductToOutProductConverter productConverter;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductConverter(ProductToOutProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    /**
     * Без него по умолчанию вернет код ошибки 500 - ошибка на сервере
     * @param id - product id
     * @return - json объект OutProduct или если нет, объект исключения NotFoundException "404, "The Product with id=99 is not found!""
     */
    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutProduct findById(@PathVariable("id") long id) {
        return productConverter.convert(productService.findByIdOptional(id));
    }

}
