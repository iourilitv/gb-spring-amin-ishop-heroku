package ru.geekbrains.spring.ishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.informing.TextTemplates;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;
import ru.geekbrains.spring.ishop.repository.OrderItemRepository;
import ru.geekbrains.spring.ishop.repository.OrderRepository;
import ru.geekbrains.spring.ishop.repository.OrderStatusRepository;
import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;
import ru.geekbrains.spring.ishop.rest.services.OutEntityService;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import ru.geekbrains.spring.ishop.utils.SystemDelivery;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ShoppingCartService cartService;
    private final DeliveryService deliveryService;
    private final IUserService userService;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UtilFilter utilFilter;
    private final OutEntityService outEntityService;
    private final OrderSubject orderSubject;

    @Transactional
    public Page<Order> findAll(OrderFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return orderRepository.findAll(filter.getSpec(), pageRequest);
    }

    //TODO for REST only temporarily
    @Transactional(readOnly = true)
    public Order findByIdOptional(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("The Order with id=" + id + " is not found!"));
    }
    //TODO replace with findByIdOptional without renaming
    @Transactional
    public Order findById(Long id) {
        return orderRepository.getOne(id);
    }

    public OrderStatus findOrderStatusByTitle(String title) {
        return orderStatusRepository.getOrderStatusByTitleEquals(title);
    }

    @Transactional
    public SystemOrder getSystemOrderForSession(HttpSession session, Long orderId) {
        SystemOrder systemOrder;
        //если в сессии нет текущего заказа(формируем новый заказ)
        if(orderId == 0) {
            ShoppingCart cart = cartService.getShoppingCartForSession(session);
            User user = (User)session.getAttribute("user");
            SystemDelivery systemDelivery = new SystemDelivery(user.getPhoneNumber(),
                    user.getDeliveryAddress(), BigDecimal.valueOf(100),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            BigDecimal totalCosts = cart.getTotalCost().add(systemDelivery.getDeliveryCost());
            systemOrder = new SystemOrder(user, cart.getCartItems(),
                    cart.getTotalCost(), totalCosts, systemDelivery);
        //если вызываем существующий заказ
        } else {
            //клонируем заказ в объект текущего заказа
            systemOrder = new SystemOrder(orderRepository.getOne(orderId));
        }
        session.setAttribute("order", systemOrder);
        return systemOrder;
    }

    public void rollBackToCart(HttpSession session) {
        cartService.rollBackToCart(session);
    }

    @Transactional
    public Order saveNewOrder(SystemOrder systemOrder) {
        Order order = createNewDraftOrUpdateOrder(systemOrder);
        orderRepository.save(order);
        order.setOrderItems(saveOrderItems(systemOrder.getOrderItems(), order));
        Delivery delivery = createDelivery(systemOrder.getSystemDelivery(), order);
        deliveryService.save(delivery);
        order.setDelivery(delivery);
        orderSubject.requestToSendMessage(order, TextTemplates.NEW_ORDER_CREATED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String titleOfNewOrderStatus) {
        Order order = findByIdOptional(orderId);
        OrderStatus orderStatus = findOrderStatusByTitle(titleOfNewOrderStatus);
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        orderSubject.requestToSendMessage(order, TextTemplates.ORDER_STATUS_CHANGED);
        return order;
    }

    @Transactional
    public void updateDelivery(SystemOrder systemOrder) {
        //получаем экземпляр заказа из БД
        Order order = orderRepository.getOne(systemOrder.getId());
        //получаем экземпляр объекта доставка из БД и изменяем его
        Delivery delivery = createDelivery(systemOrder.getSystemDelivery(), order);
        //сохраняем объект доставка в БД
        deliveryService.save(delivery);
        //записываем в заказ обновленный объект доставки
        order.setDelivery(delivery);
        //пересчитываем totalCosts
        order.setTotalCosts(order.getTotalItemsCosts().add(delivery.getDeliveryCost()));
        //сохраняем обновленный заказ в БД
        orderRepository.save(order);
    }

    private Delivery createDelivery(SystemDelivery systemDelivery, Order order) {
        Delivery delivery;
        if(systemDelivery.getId() == null) {
            delivery = new Delivery();
            delivery.setOrder(order);
        } else {
            delivery = deliveryService.findById(systemDelivery.getId());
        }
        delivery.setPhoneNumber(systemDelivery.getPhoneNumber());
        delivery.setDeliveryAddress(systemDelivery.getDeliveryAddress());
        delivery.setDeliveryCost(systemDelivery.getDeliveryCost());
        delivery.setDeliveryExpectedAt(systemDelivery.getDeliveryExpectedAt());
        delivery.setDeliveredAt(systemDelivery.getDeliveredAt());
        return delivery;
    }

    Order createNewDraftOrUpdateOrder(SystemOrder systemOrder) {
        Order order;
        if(systemOrder.getId() == null) {
            order = new Order();
            order.setOrderStatus(orderStatusRepository
                    .getOrderStatusByTitleEquals(OrderStatus.Statuses.Created.name()));
        } else {
            order = orderRepository.getOne(systemOrder.getId());
            order.setOrderStatus(systemOrder.getOrderStatus());
        }
        User user = userService.findByUserName(systemOrder.getUser().getUserName());
        order.setUser(user);
        order.setTotalItemsCosts(systemOrder.getTotalItemsCosts());
        order.setTotalCosts(systemOrder.getTotalCosts());
        return order;
    }

    @Transactional
    public void delete(Long orderId) {
        orderItemRepository.deleteOrderItemsByOrderId(orderId);
        deliveryService.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    //TODO заменить на comparator
    public boolean isOrderSavedCorrectly(Order order, SystemOrder systemOrder) {
        //простая проверка сохраненного заказа
        return order.getOrderItems().size() == systemOrder.getOrderItems().size() &&
                order.getTotalItemsCosts().equals(systemOrder.getTotalItemsCosts()) &&
                order.getTotalCosts().equals(systemOrder.getTotalCosts());
    }

    private List<OrderItem> saveOrderItems(List<OrderItem> cartItems, Order order) {
        for (OrderItem i : cartItems) {
            i.setOrder(order);
            orderItemRepository.save(i);
        }
        return orderItemRepository.findAllOrderItemsByOrder(order);
    }

    public List<OrderStatus> findAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    public OutEntity convertOrderToOutEntity(Order order) {
        return outEntityService.convertEntityToOutEntity(order);
    }

    public Order changeOrderStatus(Long orderId, String newValue) {
        Order order = findByIdOptional(orderId);
        order.setOrderStatus(findOrderStatusByTitle(newValue));
        return order;
    }
}
