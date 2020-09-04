package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderResource extends AbstractResource {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    //FIXME
//    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public OutOrder findById(@PathVariable("id") long id) {
//        return orderConverter.convert(orderService.findByIdOptional(id));
//    }

}
