package com.dt.restaurant_service.restaurant.service;

import com.dt.restaurant_service.restaurant.dto.RestaurantRequest;
import com.dt.restaurant_service.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponse getRestaurantById(Long id);
}
