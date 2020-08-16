package ru.geekbrains.spring.ishop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    //NOT NULL
    private Order order;

    @Column(name = "phone_number")
    //NOT NULL
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    //NOT NULL
    private Address deliveryAddress;

    @Column(name = "delivery_cost")
    //NULL DEFAULT NULL
    private BigDecimal deliveryCost;

    @Column(name = "delivery_expected_at")
    //DATETIME NULL DEFAULT NULL
    private LocalDateTime deliveryExpectedAt;

    @Column(name = "delivered_at")
    //DATETIME NULL DEFAULT NULL
    private LocalDateTime deliveredAt;

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", orderId=" + order.getId() +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", deliveryAddress=" + deliveryAddress +
                ", deliveryCost=" + deliveryCost +
                ", deliveryExpectedAt=" + deliveryExpectedAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
