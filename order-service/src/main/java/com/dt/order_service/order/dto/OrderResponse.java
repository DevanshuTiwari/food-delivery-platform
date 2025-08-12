package com.dt.order_service.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long customerId,
        Long restaurantId,
        String status,
        LocalDateTime orderDate,
        List<OrderItemResponse> items
) {}
