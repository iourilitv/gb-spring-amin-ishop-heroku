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
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryFilter categoryFilter;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              CategoryFilter categoryFilter) {
        this.categoryService = categoryService;
        this.categoryFilter = categoryFilter;
    }

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/category/all";
    }

    @GetMapping("/all")
    public String categoriesList(@RequestParam Map<String, String> params,
                                 Model model) {
        //инициируем настройки фильтра
        categoryFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Category> page = categoryService.findAll(categoryFilter, "id");//TODO id -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", categoryFilter.getFilterDefinition());
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Categories");
        return "amin/admin/categories";
    }

    @GetMapping("/create")
    public RedirectView createNewCategory(Model model) {
        model.addAttribute("category", new Category());
        return new RedirectView("/amin/admin/category/edit/0/cat_id");
    }

    @GetMapping("/edit/{cat_id}/cat_id")
    public String editCategory(@PathVariable Short cat_id, Model model, HttpSession session) {
        Category category;
        if(cat_id != 0) {
            category = categoryService.findById(cat_id);
        } else {
            category = (Category) session.getAttribute("category");
        }
        model.addAttribute("category", category);

        //TODO доделать как ProductController

        return "amin/admin/category-form";
    }

    @GetMapping("/delete/{cat_id}/cat_id")
    public String deleteCategory(@PathVariable Short cat_id) {
        Category category = categoryService.findById(cat_id);
        categoryService.delete(category);
        return "redirect:/admin/category/all";
    }

    @PostMapping("/process/create")
    public RedirectView processCreateCategory(@ModelAttribute @Valid Category category,
                                BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            //FIXME
//            return "redirect:/admin/product/category/form";
//        }
        categoryService.save(category);
        return new RedirectView("/amin/admin/category/all");
    }

    @PostMapping("/process/edit")
    public RedirectView processUpdateCategory(@ModelAttribute @Valid Category category,
                                      BindingResult bindingResult, HttpSession session) {
        //TODO доделать

        return new RedirectView("/amin/admin/category/all");
    }

}
