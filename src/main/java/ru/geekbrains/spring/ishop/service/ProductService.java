package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.entity.ProductImage;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.ProductImageRepository;
import ru.geekbrains.spring.ishop.repository.ProductRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final OutEntityService outEntityService;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final UtilFilter utilFilter;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(ProductFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return productRepository.findAll(filter.getSpec(), pageRequest);
    }

    //TODO for REST only temporarily
    @Transactional(readOnly = true)
    public Product findByIdOptional(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("The Product with id=" + id + " is not found!"));
    }

    //TODO replace with findByIdOptional without renaming
    public Product findById(Long id) {
        return productRepository.getOne(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void addImage(Product product, ProductImage productImage) {
        if (product.getImages() == null) {
            product.setImages(new ArrayList<>());
        }
        product.getImages().add(productImage);
    }

    public boolean isProductWithTitleExists(String title) {
        return productRepository.findFirstByTitle(title) != null;
    }

    public boolean isProductWithVendorCodeExists(String vendorCode) {
        return productRepository.findFirstByVendorCode(vendorCode) != null;
    }

    public void deleteAllProductImagesByProduct(Product product) {
        productImageRepository.deleteAllByProduct(product);
    }

    public OutEntity convertProductToOutEntity(Product product) {
        return outEntityService.convertEntityToOutEntity(product);
    }

}