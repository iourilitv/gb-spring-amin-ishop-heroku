package ru.geekbrains.spring.ishop.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.exception.ErrorResponse;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
//import ru.geekbrains.spring.ishop.exception.ProductErrorResponse;
import ru.geekbrains.spring.ishop.rest.converters.ProductToOutProductConverter;
import ru.geekbrains.spring.ishop.rest.outentities.OutProduct;
import ru.geekbrains.spring.ishop.rest.resources.AbstractResource;
import ru.geekbrains.spring.ishop.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource extends AbstractResource {
    Logger log = LoggerFactory.getLogger(ProductResource.class);

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

//    //указываем формат данных в представлении REST ресурса
//    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)//produces = "application/json"
//    public List<Product> findAll() {
//        return productService.getAll();
//    }

    /**
     * Без него по умолчанию вернет код ошибки 500 - ошибка на сервере
     * @param id - product id
     * @return - json объект OutProduct или если нет, объект исключения NotFoundException "404, "The Product with id=99 is not found!""
     */
//    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Product findById(@PathVariable("id") long id) {
//        return productService.findByIdOptional(id)
//                .orElseThrow(() -> new NotFoundException("The page is not found!"));
//    }
    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutProduct findById(@PathVariable("id") long id) {
//        Product product = productService.findByIdOptional(id);
//        OutProduct outProduct = productConverter.convert(product);
//        String className = product.getClass().getSimpleName();
//        return Optional.ofNullable(outProduct)
//                .orElseThrow(() -> new NotFoundException("The " + className + " with id=" + id + " is not found!"));
        return productConverter.convert(productService.findByIdOptional(id));
//        return Optional.ofNullable(outProduct)
//                .orElseThrow(() -> new NotFoundException("The Product with id=" + id + " is not found!"));
//        assert outProduct != null;
//        log.info(outProduct.getStore());
//        return Optional.of(outProduct)
//                .orElseThrow(() -> new NotFoundException("The Product with id=" + id + " is not found!"));
    }

//    @GetMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Product newProduct() {
//        Product product = new Product();
//        product.setId(200l);
//        product.setTitle("Title 200");
//
//        Category category = new Category();
//        category.setId((short) 1);
//        category.setTitle("Category title");
//
//        product.setCategory(category);
//        product.setShortDescription("Short Description 200");
//        product.setFullDescription("Full Description 200");
//        product.setPrice(new BigDecimal(200));
//        return product;
//    }
//    @GetMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
//    public OutProduct newProduct() {
//        return productConverter.convert(productService.findById(100l));
//    }

//    @PostMapping
//    public void createProduct(@RequestBody Product product) {
//        //если в запросе указан id - такой объект уже есть в БД
//        if (product.getId() != null) {
//            throw new IllegalArgumentException("A Product with this id already exists!");
//        }
//        productService.insert(product);
//    }

//    @PutMapping
//    public void updateProduct(@RequestBody Product product) {
//        productService.update(product);
//    }

//    @DeleteMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void delete(@PathVariable("id") long id) {
//        productService.deleteById(id);
//    }





}
