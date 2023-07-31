package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.model.UserModel;

import java.util.List;

public interface IRestEmployeeServicePort {

    void saveRestEmployee(RestEmployeeModel restaurantEmployeeModel);

    List<RestEmployeeModel> getAllRestEmployees();
}