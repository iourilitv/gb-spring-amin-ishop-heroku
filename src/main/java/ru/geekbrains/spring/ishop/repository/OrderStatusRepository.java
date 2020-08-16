package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.OrderStatus;

@Repository
public interface OrderStatusRepository extends
        JpaRepository<OrderStatus, Short>, JpaSpecificationExecutor<OrderStatus> {

    OrderStatus getOrderStatusByTitleEquals(String statusTitle);

}
