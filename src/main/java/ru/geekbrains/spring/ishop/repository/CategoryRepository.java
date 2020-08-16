package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.Category;

@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Short>, JpaSpecificationExecutor<Category> {
}
