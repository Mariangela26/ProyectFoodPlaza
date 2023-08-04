package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class DishRequestDto {

    @NotBlank(message = "The name is required")
    private String name;
    @NotBlank(message = "The price is required")
    @Pattern(regexp = "^[1-9]\\d*$", message = "The price must be a positive integer greater than zero")
    private String price;
    @NotBlank(message = "The description is required")
    private String description;
    @NotBlank(message = "The image url is required")
    private String urlImage;

    private Boolean active;

    @NotNull(message = "The restaurant_id cannot be null")
    @Min(value = 1, message = "The restaurant_id must be greater than zero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long restaurantId;

    @NotNull(message = "the category_id cannot be null")
    @Min(value = 1, message = "The category_id must be greater than zero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long categoryId;
}
