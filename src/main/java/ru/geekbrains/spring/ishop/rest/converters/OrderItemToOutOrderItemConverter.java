package ru.geekbrains.spring.ishop.rest.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.OrderItem;
import ru.geekbrains.spring.ishop.rest.outentities.OutOrderItem;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

@Service
public class OrderItemToOutOrderItemConverter implements Converter<OrderItem, OutOrderItem> {

    private OutEntityService outEntityService;

    @Autowired
    public void setOutEntityService(OutEntityService outEntityService) {
        this.outEntityService = outEntityService;
    }

    @Override
    public OutOrderItem convert(@Nullable OrderItem orderItem) {
        assert orderItem != null;
        return outEntityService.createOutOrderItem(orderItem);
    }

}
