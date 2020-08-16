package ru.geekbrains.spring.ishop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    //NOT NULL
    private Product product;

    @Column(name = "item_price")
    //NOT NULL
    private BigDecimal itemPrice;

    @Column(name = "quantity")
    //NOT NULL
    private int quantity;

    @Column(name = "item_costs")
    //NOT NULL
    private BigDecimal itemCosts;

    @ManyToOne
    @JoinColumn(name = "order_id")
    //NOT NULL
    private Order order;

    @Override
    public String toString() {
        long orderId = order == null ? 0 : order.getId();
        return "OrderItem{" +
                "id=" + id +
                ", productId=" + product.getId() +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", itemCosts=" + itemCosts +
                ", orderId=" + orderId +
                '}';
    }

}
