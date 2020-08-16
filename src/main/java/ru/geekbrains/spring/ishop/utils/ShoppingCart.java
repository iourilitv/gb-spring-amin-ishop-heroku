package ru.geekbrains.spring.ishop.utils;

import lombok.Data;
import ru.geekbrains.spring.ishop.entity.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private List<OrderItem> cartItems;
    private BigDecimal totalCost;

    public ShoppingCart() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

}
