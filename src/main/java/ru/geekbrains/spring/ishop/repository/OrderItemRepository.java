package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.entity.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends
        JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {

    List<OrderItem> findAllOrderItemsByOrder(Order order);

    void deleteOrderItemsByOrderId(Long id);
}
