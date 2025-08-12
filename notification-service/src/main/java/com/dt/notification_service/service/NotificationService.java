package com.dt.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    @KafkaListener(topics = "orders_topic", groupId = "notification-group")
    public void listenToOrders(String message) {
        log.info("Received new order notification: {}", message);

        // In a real application, you would add logic here to:
        // 1. Parse the JSON message into an Order object.
        // 2. Send an email or SMS to the customer.
    }
}
