package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(RestaurantModel restaurantModel);

    RestaurantModel getRestaurantById(Long id);

    RestaurantModel getRestaurantByIdOwner(Long id_propietario);

    List<RestaurantModel> getAllRestaurants();

    List<RestaurantModel> getRestaurantWithPagination(Integer page, Integer size);

    void deleteRestaurantById(Long id);
}

