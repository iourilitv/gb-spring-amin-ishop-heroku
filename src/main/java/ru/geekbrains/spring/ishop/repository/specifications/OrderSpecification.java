package ru.geekbrains.spring.ishop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.ishop.entity.Order;

public class OrderSpecification {

    //TODO добавить сортировку по стране, городу и части адреса, пользователю и т.п.
    //метод возвращает спецификацию для фильтра "товары категории"
    public static Specification<Order> orderStatusIdEquals(Short value) {
        return (Specification<Order>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("orderStatus").get("id"), value);
    }

    public static Specification<Order> userNameEquals(String value) {
        return (Specification<Order>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("user").get("userName"), value);
    }

}
