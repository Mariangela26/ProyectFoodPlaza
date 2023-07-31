package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.spi.persistence.IRestEmployeePersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestEmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestEmployeeJpaAdapter implements IRestEmployeePersistencePort {

    private final IRestEmployeeRepository restEmployeeRepository;
    private final IRestEmployeeEntityMapper restEmployeeEntityMapper;


    @Override
    public RestEmployeeModel saveRestEmployee(RestEmployeeModel restEmployeeModel) {
        RestEmployeeEntity restEmployeeEntity = restEmployeeRepository.save(restEmployeeEntityMapper.toEntity(restEmployeeModel));
        return restEmployeeEntityMapper.toRestEmployeeModel(restEmployeeEntity);
    }

    @Override
    public List<RestEmployeeModel> getAllRestEmployees() {
        List<RestEmployeeEntity> restEmployeeEntityList = restEmployeeRepository.findAll();
        if(restEmployeeEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return restEmployeeEntityMapper.toRestEmployeeModelList(restEmployeeEntityList);
    }

    @Override
    public RestEmployeeModel findByUserId(String idEmployee) {
        Optional<RestEmployeeEntity> restEmployeeEntityOptional = restEmployeeRepository.findByUserId(idEmployee);
        RestEmployeeEntity restEmployeeEntity = restEmployeeEntityOptional.orElse(null);
        return restEmployeeEntityMapper.toRestEmployeeModel(restEmployeeEntity);
    }
}