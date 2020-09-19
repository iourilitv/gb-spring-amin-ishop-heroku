package ru.geekbrains.spring.ishop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends AbstractEntity {
    //TODO добавить в таблицу и AbstractEntity поля createdAt, updatedAt
    public enum Fields {id, product, itemPrice, quantity, itemCosts, order}

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
