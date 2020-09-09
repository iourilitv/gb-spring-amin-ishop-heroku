package ru.geekbrains.spring.ishop.utils;

import lombok.Data;
import ru.geekbrains.spring.ishop.entity.OrderItem;
import ru.geekbrains.spring.ishop.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private User user;
    private List<OrderItem> cartItems;
    private BigDecimal totalCost;

    public ShoppingCart() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

    public ShoppingCart(User user) {
        this.user = user;
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }
}
