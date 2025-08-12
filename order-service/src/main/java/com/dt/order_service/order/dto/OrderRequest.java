package com.dt.order_service.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Customer ID cannot be null")
        Long customerId,

        @NotNull(message = "Restaurant ID cannot be null")
        Long restaurantId,

        @NotEmpty(message = "Order must contain at least one item")
        List<OrderItemRequest> items
) {}
