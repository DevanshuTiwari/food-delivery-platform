package com.dt.restaurant_service.restaurant.service;

import com.dt.restaurant_service.exception.ResourceNotFoundException;
import com.dt.restaurant_service.restaurant.dto.RestaurantRequest;
import com.dt.restaurant_service.restaurant.dto.RestaurantResponse;
import com.dt.restaurant_service.restaurant.model.Restaurant;
import com.dt.restaurant_service.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {
        // DTO to Entity
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setAddress(restaurantRequest.address());
        restaurant.setCuisineType(restaurantRequest.cuisineType());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // Saved Entity to DTO
        return new RestaurantResponse(
                savedRestaurant.getId(),
                savedRestaurant.getName(),
                savedRestaurant.getAddress(),
                savedRestaurant.getCuisineType()
        );
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> new RestaurantResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getCuisineType()))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType()
        );
    }
}
