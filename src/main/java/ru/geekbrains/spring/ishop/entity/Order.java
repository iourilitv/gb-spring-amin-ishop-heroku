package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    //NotNull
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //NotNull
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name = "total_items_costs")
    //NotNull
    private BigDecimal totalItemsCosts;

    @Column(name = "total_costs")
    //NotNull
    private BigDecimal totalCosts;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    //NULL DEFAULT NULL
    private Delivery delivery;

    @Column(name = "created_at")
    @CreationTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", userId=" + user.getId() +
                ", orderItems=" + orderItems +
                ", totalItemsCosts=" + totalItemsCosts +
                ", totalCosts=" + totalCosts +
                ", delivery=" + delivery +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
