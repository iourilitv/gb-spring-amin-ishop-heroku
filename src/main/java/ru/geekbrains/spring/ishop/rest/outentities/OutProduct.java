package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutProduct extends AbstractOutEntity {
    private Long id;

    private String categoryTitle;

    private String vendorCode;

    private String title;

    private BigDecimal price;

    private String shortDescription;

    private String fullDescription;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
