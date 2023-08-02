package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.domain.model.RestaurantModel;

public interface IRestaurantRequestMapper {

    RestaurantModel toRestaurant(RestaurantRequestDto restaurantRequestDto);


    RestaurantRequestDto toRequest(RestaurantModel restaurantModel);

}
