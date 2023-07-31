package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.model.UserModel;
import java.util.List;

public interface IRestEmployeePersistencePort {
    RestEmployeeModel saveRestEmployee(RestEmployeeModel restEmployeeModel);

    List<RestEmployeeModel> getAllRestEmployees();

    RestEmployeeModel findByUserId(String idEmployee);
}