package ru.geekbrains.spring.ishop.rest.services;

import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.rest.outentities.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutEntityService {

    public OutProduct createOutProduct(Product product) {
        OutProduct out = new OutProduct();
        out.setId(product.getId());
        out.setCategoryTitle(product.getCategory().getTitle());
        out.setVendorCode(product.getVendorCode());
        out.setTitle(product.getTitle());
        out.setPrice(product.getPrice());
        out.setShortDescription(product.getShortDescription());
        out.setFullDescription(product.getFullDescription());
        out.setCreatedAt(product.getCreateAt());
        out.setUpdatedAt(product.getUpdateAt());
        return out;
    }

    public OutOrder createOutOrder(Order order) {
        OutOrder out = new OutOrder();
        out.setId(order.getId());
        out.setOrderStatusTitle(order.getOrderStatus().getTitle());
        out.setOutUser(createOutUser(order.getUser()));
        out.setOutOrderItems(getOutOrderItemList(order.getOrderItems()));
        out.setTotalItemsCosts(order.getTotalItemsCosts());
        out.setTotalCosts(order.getTotalCosts());
        out.setOutDelivery(createOutDelivery(order.getDelivery()));
        out.setCreatedAt(order.getCreatedAt());
        out.setUpdatedAt(order.getUpdatedAt());
        return out;
    }

    public OutUser createOutUser(User user) {
        OutUser out = new OutUser();
        out.setId(user.getId());

        return out;
    }

    private List<OutOrderItem> getOutOrderItemList(List<OrderItem> orderItems) {
        List<OutOrderItem> outList = new ArrayList<>();
        orderItems.forEach(item -> outList.add(createOutOrderItem(item)));
        return outList;
    }

    public OutOrderItem createOutOrderItem(OrderItem orderItem) {
        OutOrderItem out = new OutOrderItem();
        out.setId(orderItem.getId());
        out.setOutProduct(createOutProduct(orderItem.getProduct()));
        out.setItemPrice(orderItem.getItemPrice());
        out.setQuantity(orderItem.getQuantity());
        out.setItemCosts(orderItem.getItemCosts());
        out.setOrderId(orderItem.getOrder().getId());
        return out;
    }

    public OutDelivery createOutDelivery(Delivery delivery) {
        OutDelivery out = new OutDelivery();
        out.setId(delivery.getId());
        out.setOrderId(delivery.getOrder().getId());
        out.setPhoneNumber(delivery.getPhoneNumber());
        out.setOutDeliveryAddress(createOutDeliveryAddress(delivery));
        out.setDeliveryCost(delivery.getDeliveryCost());
        out.setDeliveryExpectedAt(delivery.getDeliveryExpectedAt());
        out.setDeliveredAt(delivery.getDeliveredAt());
        return out;
    }

    private String createOutDeliveryAddress(Delivery delivery) {
        return "Address: {id=" +
                delivery.getDeliveryAddress().getId() +
                ", Country=" +
                delivery.getDeliveryAddress().getCountry() +
                ", City=" +
                delivery.getDeliveryAddress().getCity() +
                ", Address=" +
                delivery.getDeliveryAddress().getAddress() +
                "}";
    }


}
