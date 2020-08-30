package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private UtilFilter utilFilter;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

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
}
