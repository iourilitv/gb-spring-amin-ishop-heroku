package ru.geekbrains.spring.ishop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.ishop.entity.User;

public class UserSpecification {

    //метод возвращает спецификацию для фильтра "пользователи с определенной ролью"
    public static Specification<User> userRoleIsMember(Short value) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.isMember(value, root.get("roles"));
    }

}
