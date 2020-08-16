package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.repository.AddressRepository;
import ru.geekbrains.spring.ishop.repository.OrderItemRepository;
import ru.geekbrains.spring.ishop.repository.OrderRepository;
import ru.geekbrains.spring.ishop.repository.OrderStatusRepository;
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
public class OrderService {
    private final ShoppingCartService cartService;
    private final DeliveryService deliveryService;
    private final IUserService userService;
    private final AddressRepository addressRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UtilFilter utilFilter;

    @Autowired
    public OrderService(ShoppingCartService cartService, DeliveryService deliveryService, IUserService userService, AddressRepository addressRepository, OrderStatusRepository orderStatusRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, UtilFilter utilFilter) {
        this.cartService = cartService;
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.addressRepository = addressRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.utilFilter = utilFilter;
    }

    @Transactional
    public Page<Order> findAll(OrderFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return orderRepository.findAll(filter.getSpec(), pageRequest);
    }

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
        //создаем черновик заказа
        Order order = createNewDraftOrUpdateOrder(systemOrder);
        //сохраняем черновик заказа, чтобы получить orderId
        orderRepository.save(order);
        //сохраняем в БД и записываем в заказ обновленные объекты элементов заказа
        order.setOrderItems(saveOrderItems(systemOrder.getOrderItems(), order));
        //создаем новый объект доставки
        Delivery delivery = createDelivery(systemOrder.getSystemDelivery(), order);
        //сохраняем объект доставка в БД
        deliveryService.save(delivery);
        //записываем в заказ обновленный объект доставки
        order.setDelivery(delivery);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(SystemOrder systemOrder, OrderStatus newOrderStatus) {
        //получаем экземпляр заказа из БД
        Order order = orderRepository.getOne(systemOrder.getId());
        //находим в базе новый статус заказа
        OrderStatus orderStatus = findOrderStatusByTitle(newOrderStatus.getTitle());
        //записываем в заказ обновленный объект статуса
        order.setOrderStatus(orderStatus);
        //сохраняем обновленный заказ в БД
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public boolean updateOrderItems(SystemOrder systemOrder) {
        //получаем экземпляр заказа из БД
        Order order = orderRepository.getOne(systemOrder.getId());
        //удаляем предыдущий список товаров заказа
        orderItemRepository.deleteOrderItemsByOrderId(order.getId());
        //сохраняем в БД и записываем в заказ обновленные объекты элементов заказа
        order.setOrderItems(saveOrderItems(systemOrder.getOrderItems(), order));
        //сохраняем обновленный заказ в БД
        orderRepository.save(order);
        return true;
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
                    .getOrderStatusByTitleEquals("Created"));
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

    @Transactional
    public void cancelOrder(Long orderId) {
        //просто меняем статус на "Canceled" и оставляем заказ в списке
        Order order = findById(orderId);
        order.setOrderStatus(findOrderStatusByTitle("Canceled"));
        orderRepository.save(order);
    }

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

    private void recalculate(Order order) {
        BigDecimal totalItemsCost = BigDecimal.ZERO;
        if(order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem o : order.getOrderItems()) {
                recalculateItemCosts(o);
                totalItemsCost = totalItemsCost.add(o.getItemCosts());
            }
        }
        order.setTotalItemsCosts(totalItemsCost);
        order.setTotalCosts(totalItemsCost.add(order.getDelivery().getDeliveryCost()));
    }

    private void recalculateItemCosts(OrderItem orderItem) {
        orderItem.setItemCosts(BigDecimal.ZERO);
        orderItem.setItemCosts(orderItem.getItemPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    }

    public OrderItem findOrderItemByProdId(List<OrderItem> orderItems, Long prodId) {
        OrderItem orderItem = null;
        for (OrderItem o : orderItems) {
            if(o.getProduct().getId().equals(prodId)) {
                orderItem = o;
            }
        }
        return orderItem;
    }

}

//Integer.valueOf(Year.now().toString()), Month.JUNE, MonthDay.of(Month.JUNE, 3))));
