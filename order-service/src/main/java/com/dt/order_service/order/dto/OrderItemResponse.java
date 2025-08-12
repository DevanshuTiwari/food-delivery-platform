package com.dt.order_service.order.dto;

public record OrderItemResponse(
        Long id,
        String menuItemId,
        Integer quantity,
        Double price
) {}
