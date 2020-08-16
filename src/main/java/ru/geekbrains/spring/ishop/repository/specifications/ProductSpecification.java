package ru.geekbrains.spring.ishop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.ishop.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    //метод возвращает спецификацию для фильтра "больше минимальной цены"
    public static Specification<Product> priceGEThan(BigDecimal value) {
        //root - здесь Product - объект спецификации
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    //метод возвращает спецификацию для фильтра "меньше минимальной цены"
    public static Specification<Product> priceLEThan(BigDecimal value) {
        //root - здесь Product - объект спецификации
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }

    //метод возвращает спецификацию для фильтра "товары категории"
    public static Specification<Product> categoryIdEquals(Short value) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("category").get("id"), value);
    }

}
