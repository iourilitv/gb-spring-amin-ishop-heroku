package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/order_status")
public class OrderStatusController {

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/order_status/all";
    }

    @GetMapping("/all")
    public String orderStatusesList(@RequestParam Map<String, String> params,
                            Model model) {
        return "amin/admin/order-statuses";
    }
    
}
