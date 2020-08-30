package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.rest.converters.OrderToOutOrderConverter;
import ru.geekbrains.spring.ishop.rest.outentities.OutOrder;
import ru.geekbrains.spring.ishop.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderResource extends AbstractResource {

    private OrderService orderService;

    private OrderToOutOrderConverter orderConverter;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderConverter(OrderToOutOrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    /**
     * Без него по умолчанию вернет код ошибки 500 - ошибка на сервере
     * @param id - order id
     * @return - json объект OutOrder или если нет, объект исключения NotFoundException "404, "The Order with id=99 is not found!""
     */
    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutOrder findById(@PathVariable("id") long id) {
        return orderConverter.convert(orderService.findByIdOptional(id));
    }

}
