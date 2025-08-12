package com.dt.restaurant_service.restaurant.dto;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String cuisineType
) {}
