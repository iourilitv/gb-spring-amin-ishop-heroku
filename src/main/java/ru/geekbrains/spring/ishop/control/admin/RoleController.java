package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.utils.filters.CategoryFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin/user_role")
public class RoleController {
//    private final CategoryService categoryService;
//    private final CategoryFilter categoryFilter;
//
//    @Autowired
//    public RoleController(CategoryService categoryService,
//                          CategoryFilter categoryFilter) {
//        this.categoryService = categoryService;
//        this.categoryFilter = categoryFilter;
//    }

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/role/all";
    }

    @GetMapping("/all")
    public String rolesList(@RequestParam Map<String, String> params,
                                 Model model) {
//        //инициируем настройки фильтра
//        categoryFilter.init(params);
//        //получаем объект страницы с применением фильтра
//        Page<Category> page = categoryService.findAll(categoryFilter, "id");//TODO id -> константы
//        //передаем в .html атрибуты:
//        //часть строки запроса с параметрами фильтра
//        model.addAttribute("filterDef", categoryFilter.getFilterDefinition());
//        //объект страницы продуктов
//        model.addAttribute("page", page);
//        //активную страницу
//        model.addAttribute("activePage", "Categories");
        return "amin/admin/roles";
    }

    @GetMapping("/create")
    public RedirectView createNewRole(Model model) {
//        model.addAttribute("category", new Category());
        return new RedirectView("/amin/admin/role/edit/0/role_id");
    }

    @GetMapping("/edit/{role_id}/role_id")
    public String editRole(@PathVariable Long role_id, Model model, HttpSession session) {
//        Category category;
//        if(cat_id != 0) {
//            category = categoryService.findById(cat_id);
//        } else {
//            category = (Category) session.getAttribute("category");
//        }
//        model.addAttribute("category", category);

        //TODO доделать как ProductController

        return "amin/admin/role-form";
    }

    @GetMapping("/delete/{role_id}/role_id")
    public String deleteCategory(@PathVariable Long role_id) {
//        Category category = categoryService.findById(cat_id);
//        categoryService.delete(category);
        return "redirect:/admin/role/all";
    }

    @PostMapping("/process/create")
    public RedirectView updateCategory(@ModelAttribute @Valid Category category,
                                BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            //FIXME
//            return "redirect:/admin/product/category/form";
//        }
//        categoryService.save(category);
        return new RedirectView("/amin/admin/role/all");
    }

    @PostMapping("/process/edit")
    public RedirectView updateProduct(@ModelAttribute @Valid Product product,
                                      BindingResult bindingResult, HttpSession session) {
        //TODO доделать

        return new RedirectView("/amin/admin/category/all");
    }

}
