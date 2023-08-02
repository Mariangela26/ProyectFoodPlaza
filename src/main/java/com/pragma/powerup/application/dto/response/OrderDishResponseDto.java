package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.CategoryModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishResponseDto {
    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private CategoryModel categoryId;

    private String quantity;

}