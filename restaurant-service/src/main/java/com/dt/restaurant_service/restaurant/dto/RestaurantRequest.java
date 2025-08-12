package com.dt.restaurant_service.restaurant.dto;

public record RestaurantRequest(
        String name,
        String address,
        String cuisineType
) {}
