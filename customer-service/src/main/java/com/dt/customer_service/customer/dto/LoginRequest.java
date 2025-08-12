package com.dt.customer_service.customer.dto;

public record LoginRequest(
        String email,
        String password
) {}
