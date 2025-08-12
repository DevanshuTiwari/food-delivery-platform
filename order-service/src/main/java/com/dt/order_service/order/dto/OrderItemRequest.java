package com.dt.order_service.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotBlank(message = "id cannot be blank")
        String menuItemId,

        @Positive(message = "Quantity must be a positive value")
        Integer quantity,

        @Positive(message = "Price must be a positive value")
        Double price
) {}
