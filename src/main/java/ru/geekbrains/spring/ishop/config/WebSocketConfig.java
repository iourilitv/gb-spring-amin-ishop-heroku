package ru.geekbrains.spring.ishop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@ComponentScan("ru.geekbrains.spring.ishop")
@EnableWebSocketMessageBroker //добавляет брокера сообщений по websocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //включаем простого брокера сообщений
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // подключаем SockJS, как альтернативный вариант обмена сообщениями
        //регистрируем эндпоинт для обработки события "добавить в корзину"
        //обрабатываем в контроллере CartController
        registry.addEndpoint("/addToCart", "/changeQuantity", "/changeOrderStatus").withSockJS();
    }

}
