package ru.geekbrains.spring.ishop.informing.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitSender {
    private final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    private final String RABBIT_HOST = "localhost";
    private final static String Exchange_NAME = "proceed_to_checkout_order";

    private final Channel channel;

    public RabbitSender() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBIT_HOST);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(Exchange_NAME, "fanout");
    }

    public void sendMessage(String msg) throws IOException {
        channel.basicPublish(Exchange_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));
        logger.info("Exchange(name=" + Exchange_NAME + ") sent an message: " + msg);
    }
}
