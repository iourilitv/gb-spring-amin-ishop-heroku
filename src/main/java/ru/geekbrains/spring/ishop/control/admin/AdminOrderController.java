package ru.geekbrains.spring.ishop.control.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.entity.OrderStatus;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.utils.SystemDelivery;
import ru.geekbrains.spring.ishop.utils.SystemOrder;
import ru.geekbrains.spring.ishop.utils.filters.OrderFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final OrderFilter orderFilter;

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/order/all";
    }

    @GetMapping("/all")
    public String showAll(@RequestParam Map<String, String> params,
                          Model model, HttpSession session) {
        //удаляем атрибут заказа
        session.removeAttribute("order");
        //инициируем настройки фильтра - админ режим
        orderFilter.init(params, "");
        //получаем объект страницы с применением фильтра
        //TODO created_at -> константы
        Page<Order> page = orderService.findAll(orderFilter,Order.Fields.createdAt.name());
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

        return "admin/orders-list";
    }

    @GetMapping("/show/{order_id}/order_id")
    public String showOrderDetails(@PathVariable Long order_id, ModelMap model,
                                   HttpSession session){
        SystemOrder systemOrder = orderService.getSystemOrderForSession(session, order_id);
        model.addAttribute("order", systemOrder);
        model.addAttribute("delivery", systemOrder.getSystemDelivery());
        return "admin/order-info";
    }

    @GetMapping("/edit/{order_id}/order_id")
    public String editOrder(@PathVariable Long order_id, Model model,
                            HttpSession session) {
        SystemOrder systemOrder = orderService.getSystemOrderForSession(session, order_id);
        model.addAttribute("order", systemOrder);
        model.addAttribute("orderStatuses", orderService.findAllOrderStatuses());
        model.addAttribute("orderStatus", systemOrder.getOrderStatus());
        model.addAttribute("delivery", systemOrder.getSystemDelivery());
        return "admin/order-form";
    }

    @GetMapping("/delete/{order_id}/order_id")
    public RedirectView removeOrder(@PathVariable("order_id") Long orderId) {
        orderService.delete(orderId);
        return new RedirectView("/admin/order/all");
    }

    @GetMapping("/cancel/{order_id}/order_id")
    public RedirectView cancelOrder(@PathVariable("order_id") Long orderId) {
        orderService.updateOrderStatus(orderId, OrderStatus.Statuses.Canceled.name());
        return new RedirectView("/admin/order/all");
    }

    @PostMapping("/process/update/orderStatus")
    public RedirectView processUpdateOrderStatus(@Valid @ModelAttribute("orderStatus") OrderStatus orderStatus,
                                                 BindingResult theBindingResult,
                                                 HttpSession session) {
        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
        if (!theBindingResult.hasErrors()) {
            Order order = orderService.updateOrderStatus(systemOrder.getId(), orderStatus.getTitle());
            systemOrder.setOrderStatus(order.getOrderStatus());//TODO зачем?
        }
        return new RedirectView("/admin/order/edit/" +
                systemOrder.getId() + "/order_id");
    }

    @PostMapping("/process/update/delivery")
    public RedirectView processUpdateDelivery(@Valid @ModelAttribute("delivery") SystemDelivery systemDelivery,
                                              BindingResult theBindingResult,
                                              HttpSession session) {
        SystemOrder systemOrder = (SystemOrder) session.getAttribute("order");
        if (!theBindingResult.hasErrors()) {
            systemOrder.setSystemDelivery(systemDelivery);
            //сохраняем изменение в БД
            orderService.updateDelivery(systemOrder);
        }
        return new RedirectView("/admin/order/edit/" +
                systemOrder.getId() + "/order_id");
    }

}
