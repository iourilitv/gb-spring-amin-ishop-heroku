package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@RequestMapping("/profile/cart")
public class ShoppingCartController {
    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String cartPage(Model model, HttpSession session) {
        ShoppingCart cart = cartService.getShoppingCartForSession(session);
        model.addAttribute("cart", cart);
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("activePage", "Cart");
        //добавляем общее количество товаров в корзине
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        model.addAttribute("cartItemsQuantity", cartItemsQuantity);
        return "amin/cart";
    }

    @GetMapping("/delete/{prod_id}/prod_id")
    public String removeFromCart(@PathVariable Long prod_id,  HttpServletRequest httpServletRequest) throws Throwable {
        cartService.removeItemFromCartById(httpServletRequest.getSession(), prod_id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/clear")
    public String removeFromCart(HttpServletRequest httpServletRequest) {
        cartService.getClearedCartForSession(httpServletRequest.getSession());
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

}
