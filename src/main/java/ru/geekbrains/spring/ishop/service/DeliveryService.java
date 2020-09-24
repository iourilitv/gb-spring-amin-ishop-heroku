package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.DeliveryRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OutEntityService outEntityService;

    //TODO for REST only temporarily
    @Transactional(readOnly = true)
    public Delivery findByIdOptional(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("The Delivery with id=" + id + " is not found!"));
    }
    //TODO replace with findByIdOptional without renaming
    public Delivery findById(Long id) {
        return deliveryRepository.getOne(id);
    }

    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public void delete(Delivery delivery) {
        deliveryRepository.delete(delivery);
    }

    public void deleteByOrderId(Long orderId) {
        deliveryRepository.deleteByOrderId(orderId);
    }

    public Delivery updateServerAcceptedAt(Long deliveryId, String newValue) {
        Delivery delivery = findByIdOptional(deliveryId);
        LocalDateTime localDateTime = LocalDateTime.parse(newValue);
        delivery.setDeliveredAt(localDateTime);
        return delivery;
    }

    public OutEntity convertDeliveryToOutEntity(Delivery delivery) {
        return outEntityService.convertEntityToOutEntity(delivery);
    }
}
