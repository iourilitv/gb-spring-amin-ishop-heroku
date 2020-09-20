package ru.geekbrains.spring.ishop.control;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.*;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Component
@RequestMapping("/profile/order")
@RequiredArgsConstructor
public class OrderController {
    private final CategoryService categoryService;
    private final ShoppingCartService cartService;
    private final OrderService orderService;
    private final OrderFilter orderFilter;

    @GetMapping("/all")
    public String allOrders(@RequestParam Map<String, String> params,
                            Model model, HttpSession session, Principal principal) {
        //удаляем атрибут заказа
        session.removeAttribute("order");
        //инициируем настройки фильтра - показываем только заказы этого пользователя
        orderFilter.init(params, principal.getName());

        //получаем объект страницы с применением фильтра
        //TODO created_at -> константы
        Page<Order> page = orderService.findAll(orderFilter,"createdAt");
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", orderFilter.getFilterDefinition());
        //коллекцию категорий
        categoryService.addToModelAttributeCategories(model);
        //объект страницы заказов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Profile");
        //коллекцию статусов заказа
        model.addAttribute("orderStatuses", orderService.findAllOrderStatuses());

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        //вызываем файл orders.html
        return "orders";
    }

    @GetMapping("/proceedToCheckout")
    public RedirectView proceedToCheckoutOrder() {
        return new RedirectView("/profile/order/show/0/order_id");
    }

    @GetMapping("/rollBack")
    public RedirectView proceedToRollBackToCart(HttpSession session) {
        orderService.rollBackToCart(session);
        return new RedirectView("/profile/cart");
    }

    @GetMapping("/create")
    public RedirectView createOrder(HttpSession session) {
        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
        Order order = orderService.saveNewOrder(systemOrder);
        if(order != null && orderService.isOrderSavedCorrectly(order, systemOrder)) {
            cartService.getClearedCartForSession(session);
            session.removeAttribute("order");
            return new RedirectView("/profile/order/all");
        }
        return new RedirectView("/profile/order/rollBack");
    }

    @GetMapping("/show/{order_id}/order_id")
    public String showOrderDetails(@PathVariable Long order_id, Model model,
                                   HttpSession session){
        SystemOrder systemOrder = orderService.getSystemOrderForSession(session, order_id);

        if(systemOrder.getUser().getDeliveryAddress() == null) {
            new RedirectView("/profile/form/show");
        }
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("order", systemOrder);
        model.addAttribute("delivery", systemOrder.getSystemDelivery());

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "order-details";
    }

}