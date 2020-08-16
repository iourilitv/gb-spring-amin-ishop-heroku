package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminSectionController {
    private ProductController productController;

    @Autowired
    public AdminSectionController(ProductController productController) {
        this.productController = productController;
    }

    //FIXME сделать индексную страницу для раздела admin
    @GetMapping
    public String adminSection() {
//        return "redirect:/admin/product/all";
//        return "amin/admin-index";
        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String adminIndexPage() {
        return "amin/admin/admin-index";
    }

}
