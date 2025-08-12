package com.dt.customer_service.customer.dto;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}
