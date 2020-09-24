package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.DeliveryRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UtilFilter utilFilter;
    private final OutEntityService outEntityService;

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> findAll(Sort sort) {
        return deliveryRepository.findAll(sort);
    }

    //TODO for REST only temporarily
    @Transactional(readOnly = true)
    public Delivery findByIdOptional(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("The Delivery with id=" + id + " is not found!"));
    }
    //TODO replace with findByIdOptional without renaming
    public Delivery findById(Long id) {
        return deliveryRepository.getOne(id);
    }

    public Delivery findByOrder(Order order) {
        return deliveryRepository.findByOrder(order);
    }

    public Delivery getDeliveryForSession(HttpSession session) {
        Delivery delivery;
        if (session.getAttribute("delivery") == null) {
            delivery = new Delivery();
            session.setAttribute("delivery", delivery);
        } else {
            delivery = (Delivery) session.getAttribute("delivery");
        }
        return delivery;
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
