package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Delivery;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.repository.DeliveryRepository;
import ru.geekbrains.spring.ishop.utils.filters.DeliveryFilter;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;
    private UtilFilter utilFilter;

    @Autowired
    public void setRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> findAll(Sort sort) {
        return deliveryRepository.findAll(sort);
    }

    public Page<Delivery> findAll(DeliveryFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return deliveryRepository.findAll(filter.getSpec(), pageRequest);
    }

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
}
