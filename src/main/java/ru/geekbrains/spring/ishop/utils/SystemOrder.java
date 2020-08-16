package ru.geekbrains.spring.ishop.utils;

import lombok.Data;
import ru.geekbrains.spring.ishop.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SystemOrder {
    private Long id;
    private OrderStatus orderStatus;
    private User user;
    private List<OrderItem> orderItems;
    private BigDecimal totalItemsCosts;
    private BigDecimal totalCosts;
    private SystemDelivery systemDelivery;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SystemOrder() {
    }

    public SystemOrder(Order order) {
        this.id = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.user = order.getUser();
        this.orderItems = order.getOrderItems();
        this.totalItemsCosts = order.getTotalItemsCosts();
        this.totalCosts = order.getTotalCosts();
        this.systemDelivery = new SystemDelivery(order.getDelivery());
        this.createdAt = order.getCreatedAt();
    }

    public SystemOrder(User user, List<OrderItem> orderItems, BigDecimal totalItemsCosts,
                       BigDecimal totalCosts, SystemDelivery systemDelivery) {
        this.user = user;
        this.orderItems = orderItems;
        this.totalItemsCosts = totalItemsCosts;
        this.totalCosts = totalCosts;
        this.systemDelivery = systemDelivery;
    }

    @Override
    public String toString() {
        long userId = user == null ? 0 : user.getId();
        return "SystemOrder{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                ", orderItems=" + orderItems +
                ", totalItemsCosts=" + totalItemsCosts +
                ", totalCosts=" + totalCosts +
                ", delivery=" + systemDelivery +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
