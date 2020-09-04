package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource extends AbstractResource {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    //FIXME
//    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public OutProduct findById(@PathVariable("id") long id) {
//        return productConverter.convert(productService.findByIdOptional(id));
//    }

}
