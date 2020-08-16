package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private ShoppingCartService cartService;

    @Autowired
    public void setShoppingCartService(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String indexPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session) {
        //создаем корзину, если нет и добавляем ее в сессию
        cartService.getShoppingCartForSession(session);
        return "redirect:/catalog/all";
    }

}
