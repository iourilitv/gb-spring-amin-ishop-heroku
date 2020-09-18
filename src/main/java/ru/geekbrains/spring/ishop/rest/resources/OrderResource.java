package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderResource extends AbstractResource {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{orderId}/orderId")
    public ResponseEntity<OutEntity> getOrderOutEntity(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(orderService.convertEventToOutEntity(orderService.findByIdOptional(orderId)));
    }

}
