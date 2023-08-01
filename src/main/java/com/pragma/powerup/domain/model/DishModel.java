package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishModel {

    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private Boolean active;
    private RestaurantModel restaurantId;
    private CategoryModel categoryId;


}
