package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/address")
public class AddressController {

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/address/all";
    }

    @GetMapping("/all")
    public String addressesList(@RequestParam Map<String, String> params,
                            Model model) {
        return "amin/admin/addresses";
    }

}
