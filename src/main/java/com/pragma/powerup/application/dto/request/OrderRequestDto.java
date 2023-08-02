package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter

public class OrderRequestDto {

    @NotNull(message = "The list of dishes IDs cannot be null")
    @Size(min = 1, message = "There must be at least one dish on the list")
    private List<OrderDishRequestDto> dishes;
    @NotNull(message = "The ID of restaurant cannot be null")
    private Long  restaurantId;

}