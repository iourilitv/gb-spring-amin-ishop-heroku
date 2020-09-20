package ru.geekbrains.spring.ishop.rest.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderResource extends AbstractResource {
    private final OrderService orderService;

    @GetMapping(value = "/{orderId}/orderId")
    public ResponseEntity<OutEntity> getOrderOutEntity(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(orderService.convertOrderToOutEntity(orderService.findByIdOptional(orderId)));
    }

    @PutMapping(value = "/{orderId}/orderId/changeOrderStatus/string")
    public ResponseEntity<OutEntity> changeOrderStatusField(
            @RequestBody @Valid String newValue,
            @PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(orderService.convertOrderToOutEntity(
                        orderService.changeOrderStatus(orderId, newValue)));
    }

}
