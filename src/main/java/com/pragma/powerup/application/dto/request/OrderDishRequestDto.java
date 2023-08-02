package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDishRequestDto {
    @NotNull(message = "Dish ID cannot be null")
    private Long idDishes;
    @NotNull(message = "Quantity ID cannot be null")
    private Long quantity;
}