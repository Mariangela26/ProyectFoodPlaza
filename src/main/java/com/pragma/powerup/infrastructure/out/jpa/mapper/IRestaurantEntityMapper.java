package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;

import java.util.List;

public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(RestaurantModel restaurantModel);
    RestaurantModel toRestaurantModel(RestaurantEntity restaurantEntity);

    List<RestaurantModel> toRestaurantModelList(List<RestaurantEntity> restaurantModelList);

}
