package com.dt.order_service.order.service;


import com.dt.order_service.exception.ResourceNotFoundException;
import com.dt.order_service.order.dto.OrderItemResponse;
import com.dt.order_service.order.dto.OrderRequest;
import com.dt.order_service.order.dto.OrderResponse;
import com.dt.order_service.order.model.Order;
import com.dt.order_service.order.model.OrderItem;
import com.dt.order_service.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional // Ensures the whole method runs in a single database transaction
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Verification of customer by calling customer-service
        Boolean customerExists = webClientBuilder.build()
                .get()
                .uri("http://customer-service/api/v1/customers/{id}", orderRequest.customerId())
                .retrieve()
                .bodyToMono(Object.class)
                .map(response -> true)
                .onErrorResume(e -> Mono.just(false))
                .block(); // block() method makes async call(webClient) to sync

        if (customerExists == null || !customerExists) {
            throw new ResourceNotFoundException("Customer not found with id: " + orderRequest.customerId());
        }

        // Verification of restaurant by calling restaurant-service
        Boolean restaurantExists = webClientBuilder.build()
                .get()
                .uri("http://restaurant-service/api/v1/restaurants/{id}", orderRequest.restaurantId())
                .retrieve()
                .bodyToMono(Object.class)
                .map(response -> true)
                .onErrorResume(e -> Mono.just(false))
                .block();

        if (restaurantExists == null || !restaurantExists) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + orderRequest.restaurantId());
        }

        // Main order entity creation
        Order order = new Order();
        order.setCustomerId(orderRequest.customerId());
        order.setRestaurantId(orderRequest.restaurantId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // OrderItem entities creation from Order
        orderRequest.items().forEach(itemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(itemDto.menuItemId());
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPrice(itemDto.price());
            order.addOrderItem(orderItem);
        });

        Order savedOrder = orderRepository.save(order);

        // send message to kafka after saving the order
        log.info("Sending order notification to Kafka...");
        kafkaTemplate.send("orders_topic", "Order placed with ID: " + savedOrder.getId());

        return mapToOrderResponse(savedOrder);
    }

    // Map the saved entity to a response DTO
    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getMenuItemId(),
                        item.getQuantity(),
                        item.getPrice()))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getOrderDate(),
                itemResponses
        );
    }
}
