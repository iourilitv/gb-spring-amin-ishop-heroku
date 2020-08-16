package ru.geekbrains.spring.ishop.informing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.informing.messages.AbstractMailMessage;
import ru.geekbrains.spring.ishop.informing.messages.OrderEmailMessage;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService implements INotifier {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender sender;
    private final MailMessageBuilder messageBuilder;

    @Autowired
    public MailService(JavaMailSender sender, MailMessageBuilder messageBuilder) {
        this.sender = sender;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public void addMessageToQueue(AbstractMailMessage mailMessage) {
        queueToSend.add(mailMessage);
        logger.info("******** addMessageToQueue() ***********\n" + "After adding. QueueToSend: " + queueToSend);
    }

    @Override
    @PostConstruct
    public void sendMessagesFromQueue() {
        new Thread(() -> {
            logger.info("******** sendMessageFromQueue() has been launched successfully *********");
            while (true) {
                if(!queueToSend.isEmpty()) {
                    logger.info("******** sendMessageFromQueue() ***********\n" + "QueueToSend: " + queueToSend);
                    AbstractMailMessage message = queueToSend.remove();
                    logger.info("******** Trying to send the message from queue ***********\n" + "Message subject: " + message.getSubject());
                    if(message instanceof OrderEmailMessage) {
                        sendOrderMail((OrderEmailMessage) message);
                    }

                    //the same if() for another types of AbstractMailMessage

                }
            }
        }).start();

    }

    public AbstractMailMessage createOrderMailMessage(Order order, TextTemplates subject) {
        AbstractMailMessage emailMessage = new OrderEmailMessage();
        emailMessage.setSendTo(order.getUser().getEmail());

        if(subject.equals(TextTemplates.SUBJECT_NEW_ORDER_CREATED)) {
            emailMessage.setSubject(String.format(subject.getText(), order.getId()));
        } else if (subject.equals(TextTemplates.SUBJECT_ORDER_STATUS_CHANGED)) {
            emailMessage.setSubject(String.format(subject.getText(), order.getId(), order.getOrderStatus().getTitle()));
        }
        emailMessage.setBody(messageBuilder.buildOrderEmail(order));
        emailMessage.setOrigin(order);
        return emailMessage;
    }

    private void sendMail(String email, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject(subject);
        sender.send(message);
    }

    private void sendOrderMail(OrderEmailMessage orderEmailMessage) {
        try {
            sendMail(orderEmailMessage.getSendTo(), orderEmailMessage.getSubject(), orderEmailMessage.getBody());
        } catch (MessagingException e) {
            logger.warn(TextTemplates.ERROR_CREATE_ORDER_MAIL.getText(), orderEmailMessage.getOrder().getId());
            takePauseInSec(10);
            queueToSend.add(orderEmailMessage);
        } catch (MailException e) {
            logger.warn(TextTemplates.ERROR_SEND_ORDER_MAIL.getText(), orderEmailMessage.getOrder().getId());
            takePauseInSec(10);
            queueToSend.add(orderEmailMessage);
        }
    }

    private void takePauseInSec(int period) {
        int sec = period * 1000;
        logger.info("******** takePauseInSec() ***********\n" + "Pause period(sec): " + sec);
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
