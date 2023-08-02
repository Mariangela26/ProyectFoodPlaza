package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RestEmployeeRequestDto;
import com.pragma.powerup.application.dto.response.RestEmployeeResponseDto;

import java.util.List;

public interface IRestEmployeeHandler {

    void saveRestEmployee(RestEmployeeRequestDto restaurantEmployeeRequestDto);

    List<RestEmployeeResponseDto> getAllRestEmployees();
}