package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutDelivery extends AbstractOutEntity {

    private Long id;

    private Long orderId;

    private String phoneNumber;

    private String outDeliveryAddress;

    private BigDecimal deliveryCost;

    private LocalDateTime deliveryExpectedAt;

    private LocalDateTime deliveredAt;

}
