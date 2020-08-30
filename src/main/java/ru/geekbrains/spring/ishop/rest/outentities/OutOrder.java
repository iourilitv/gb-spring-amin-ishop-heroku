package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutOrder extends AbstractOutEntity {

    private Long id;

    private String orderStatusTitle;

    private OutUser outUser;

    private List<OutOrderItem> outOrderItems;

    private BigDecimal totalItemsCosts;

    private BigDecimal totalCosts;

    private OutDelivery outDelivery;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
