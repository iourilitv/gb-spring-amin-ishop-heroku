package ru.geekbrains.spring.ishop.rest.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductResource extends AbstractResource {
    private final ProductService productService;

    @GetMapping(value = "/{productId}/productId")
    public ResponseEntity<OutEntity> getProductOutEntity(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productService.convertProductToOutEntity(productService.findByIdOptional(productId)));
    }

}
