package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.spi.persistence.IRestEmployeePersistencePort;

import java.util.List;

public class RestEmployeeUseCase implements IRestEmployeeServicePort {

    private final IRestEmployeePersistencePort restEmployeePersistencePort;

    public RestEmployeeUseCase(IRestEmployeePersistencePort restaurantEmployeePersistencePort) {
        this.restEmployeePersistencePort = restaurantEmployeePersistencePort;
    }

    @Override
    public void saveRestEmployee(RestEmployeeModel restEmployeeModel) {
        restEmployeePersistencePort.saveRestEmployee(restEmployeeModel);
    }

    @Override
    public List<RestEmployeeModel> getAllRestEmployees() {
        return restEmployeePersistencePort.getAllRestEmployees();
    }
}