package ru.geekbrains.spring.ishop.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final IUserService userService;
    private final ShoppingCartService cartService;
    private final CategoryService categoryService;

    @GetMapping
    public String indexPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        categoryService.addToModelAttributeCategories(model);
        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);

        return "profile";
    }

}
