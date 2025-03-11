package com.productstore.notify.service;

import com.productstore.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class notifyServiceImpl implements notifyService {

    private final JavaMailSender javaMailSender;

    @Override
    @KafkaListener(topics = "order-topic", groupId = "group_id")
    public void listenOrder(OrderDto orderDto) {
        log.info("Received order: {}", orderDto);
        sendOrderNotification(orderDto);
    }

    public void sendOrderNotification(OrderDto orderDto) {
        log.info("Sending order notification for order ID: {}", orderDto.getOrderId());
        String userEmail = orderDto.getUserEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(String.format("Order#%d Status:%s", orderDto.getOrderId(), orderDto.getStatus()));
        message.setText(createEmailBody(orderDto));

        javaMailSender.send(message);
        log.info("Order notification sent successfully to: {}", userEmail);
    }

    private String createEmailBody(OrderDto order) {
        log.info("Creating email body for order ID: {}", order.getOrderId());
        String emailBody = String.format(
                "Thank you for your order!\n\n" +
                        "Order ID: %d\n" +
                        "Product: %s (%s)\n" +
                        "Quantity: %d\n" +
                        "Total Price: $%.2f\n" +
                        "Status: %s\n" +
                        "Order Date: %s",
                order.getOrderId(),
                order.getProductDto().getName(), // Assume ProductDto has getName()
                order.getProductDto().getCategory(),
                order.getQuantity(),
                order.getProductDto().getPrice() * order.getQuantity(),
                order.getStatus(),
                order.getOrderDate()
        );
        log.info("Email body created successfully for order ID: {}", order.getOrderId());
        return emailBody;
    }
}