package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestPaginationResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    RestaurantResponseDto getRestaurantById(Long id);
    RestaurantResponseDto getRestaurantByIdOwner(Long id_owner);

    List<RestaurantResponseDto> getAllRestaurants();

    List<RestPaginationResponseDto> getRestWithPagination(Integer page, Integer size);
    void deleteRestaurantById(Long id);
}
