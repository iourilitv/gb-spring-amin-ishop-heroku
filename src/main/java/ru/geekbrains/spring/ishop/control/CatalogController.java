package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductFilter productFilter;

    @Autowired
    public CatalogController(ShoppingCartService cartService,
                             ProductService productService,
                             CategoryService categoryService,
                             ProductFilter productFilter) {
        this.cartService = cartService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.productFilter = productFilter;
    }

    //http://localhost:8080/shop/catalog/all
    //http://localhost:8080/shop/catalog/all?page=1&limit=6&direction=DESC&minPrice=1000&maxPrice=10000
    @GetMapping("/all")
    public String allProducts(@RequestParam Map<String, String> params,
                              Model model, HttpSession session) {
        //инициируем настройки фильтра
        productFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(productFilter,"price");//TODO price -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", productFilter.getFilterDefinition());
        //коллекцию категорий
        categoryService.addToModelAttributeCategories(model);
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Catalog");

        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем множество количеств элементов в корзине
        Map<String, Integer> quantities = cartService.getCartItemsQuantities(cart);
        model.addAttribute("quantities", quantities);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        //вызываем файл catalog.html
        return "amin/catalog";
    }

    @GetMapping("/{prod_id}/details")
    public String productDetails(@PathVariable(value = "prod_id") Long prod_id,
                                 Model model, HttpSession session) {
        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("product", productService.findById(prod_id));
        int quantity = cartService.getQuantityOfCartItemByProdId(cart, prod_id);
        model.addAttribute("quantity", quantity);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
        return "amin/product-details";
    }

}
