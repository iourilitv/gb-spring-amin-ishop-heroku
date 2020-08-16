package ru.geekbrains.spring.ishop.utils;

import lombok.Data;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SystemDelivery {
    private Long id;
    private Order order;
    private String phoneNumber;
    private Address deliveryAddress;
    private BigDecimal deliveryCost;
    private LocalDateTime deliveryExpectedAt;
    private LocalDateTime deliveredAt;

    public SystemDelivery() {
    }

    public SystemDelivery(Delivery delivery) {
        this.id = delivery.getId();
        this.order = delivery.getOrder();
        this.phoneNumber = delivery.getPhoneNumber();
        this.deliveryAddress = delivery.getDeliveryAddress();
        this.deliveryCost = delivery.getDeliveryCost();
        this.deliveryExpectedAt = delivery.getDeliveryExpectedAt();
        this.deliveredAt = delivery.getDeliveredAt();
    }

    public SystemDelivery(String phoneNumber, Address deliveryAddress,
                          BigDecimal deliveryCost, LocalDateTime deliveryExpectedAt) {
        this.phoneNumber = phoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCost = deliveryCost;
        this.deliveryExpectedAt = deliveryExpectedAt;
    }

    @Override
    public String toString() {
        long orderId = order == null ? 0 : order.getId();
        return "SystemDelivery{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", deliveryAddress=" + deliveryAddress +
                ", deliveryCost=" + deliveryCost +
                ", deliveryExpectedAt=" + deliveryExpectedAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
