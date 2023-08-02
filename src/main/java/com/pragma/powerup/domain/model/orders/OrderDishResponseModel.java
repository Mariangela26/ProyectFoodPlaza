package com.pragma.powerup.domain.model.orders;

import com.pragma.powerup.domain.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseModel {
    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private CategoryModel categoryId;
    private String quantity;
}
