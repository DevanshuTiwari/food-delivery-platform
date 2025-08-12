package com.dt.customer_service.customer.dto;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {}
