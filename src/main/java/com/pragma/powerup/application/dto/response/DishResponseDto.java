package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDto {

    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private Boolean active;
    private RestaurantModel restaurantId;
    private CategoryModel categoryId;
}
