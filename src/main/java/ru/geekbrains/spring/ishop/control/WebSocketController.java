package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.geekbrains.spring.ishop.service.OrderService;
import ru.geekbrains.spring.ishop.service.ShoppingCartService;
import ru.geekbrains.spring.ishop.utils.ShoppingCart;
import ru.geekbrains.spring.ishop.utils.websocket.Request;
import ru.geekbrains.spring.ishop.utils.websocket.Response;

import java.math.BigDecimal;

@Controller
public class WebSocketController {
    private final ShoppingCartService cartService;
    private final OrderService orderService;

    @Autowired
    public WebSocketController(ShoppingCartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @MessageMapping("/addToCart")
    @SendTo("/topic/addToCart")
    public Response sendAddToCartResponse(Request request){
        Long prodId = Long.valueOf(request.getProdId());
        int quantity = cartService.addToCart(cartService.getCart(), prodId);
        int cartItemsQuantity = cartService.getCartItemsQuantity(cartService.getCart());
        return new Response(request.getProdId(), String.valueOf(quantity), String.valueOf(cartItemsQuantity));
    }

    @MessageMapping("/changeQuantity")
    @SendTo("/topic/changeQuantity")
    public Response sendChangeQuantityResponse(Request request) throws Throwable {
        //TODO костыль, чтобы отсекать ввод некорректных данных
        int quantity;
        try {
            quantity = Integer.parseInt(request.getQuantity());
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        //если введено число меньше или равно нуля
        if(quantity <= 0) {
            //устанавливаем количество на 1
            quantity = 1;
        }

        ShoppingCart cart = cartService.getCart();
        Long prodId = Long.valueOf(request.getProdId());
        //обновляем количество элемента и пересчитываем корзину
        cartService.updateItemQuantityAndRecalculateCart(cart, prodId, quantity);
        int cartItemsQuantity = cartService.getCartItemsQuantity(cart);
        BigDecimal cartItemCost = cartService.getCartItemCostByProdId(cart, prodId);
        BigDecimal totalCost = cart.getTotalCost();
        return new Response(request.getProdId(), String.valueOf(quantity),
                String.valueOf(cartItemsQuantity), String.valueOf(cartItemCost),
                String.valueOf(totalCost));
    }

    @MessageMapping("/changeOrderStatus")
    @SendTo("/topic/changeOrderStatus")
    public Response sendChangeOrderStatusResponse(Request request) {
        String title = request.getTitle();
        Response response = new Response();
        response.setOrderStatus(orderService.findOrderStatusByTitle(title));
        return response;
    }

}
