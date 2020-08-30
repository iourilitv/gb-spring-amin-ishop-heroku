package ru.geekbrains.spring.ishop.rest.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.rest.outentities.OutOrder;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

@Service
public class OrderToOutOrderConverter implements Converter<Order, OutOrder> {

    private OutEntityService outEntityService;

    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Override
    public OutOrder convert(Order order) {
        return outEntityService.createOutOrder(order);
    }

}
