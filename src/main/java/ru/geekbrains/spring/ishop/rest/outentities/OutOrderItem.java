package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutOrderItem extends AbstractOutEntity {

    private Long id;

    private OutProduct outProduct;

    private BigDecimal itemPrice;

    private int quantity;

    private BigDecimal itemCosts;

    private Long orderId;

}
