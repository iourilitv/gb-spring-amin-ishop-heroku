package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.DeliveryRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

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

}
