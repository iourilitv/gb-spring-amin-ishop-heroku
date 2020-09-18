package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource extends AbstractResource {
    private OutEntityService outEntityService;

    private ProductService productService;

    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping(value = "/{productId}/productId")
//    public ResponseEntity<OutEntity> getProductOutEntity(@PathVariable("productId") Long productId) {
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
////                .body(outEntityService.convertEntityToOutEntity(productService.findByIdOptional(productId)));
//    }

}
