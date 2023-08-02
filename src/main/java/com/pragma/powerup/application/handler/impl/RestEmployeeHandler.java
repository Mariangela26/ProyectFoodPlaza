package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestEmployeeRequestDto;
import com.pragma.powerup.application.dto.response.RestEmployeeResponseDto;
import com.pragma.powerup.application.handler.IRestEmployeeHandler;
import com.pragma.powerup.application.mapper.IRestEmployeeRequestMapper;
import com.pragma.powerup.application.mapper.IRestEmployeeResponseMapper;
import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.model.RestEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestEmployeeHandler implements IRestEmployeeHandler {

    private final IRestEmployeeServicePort restEmployeeServicePort;

    private final IRestEmployeeRequestMapper restEmployeeRequestMapper;

    private final IRestEmployeeResponseMapper restEmployeeResponseMapper;

    @Override
    public void saveRestEmployee(RestEmployeeRequestDto restEmployeeRequestDto) {
        RestEmployeeModel restEmployeeModel = restEmployeeRequestMapper.toRestEmployeeModel(restEmployeeRequestDto);
        restEmployeeServicePort.saveRestEmployee(restEmployeeModel);

    }

    @Override
    public List<RestEmployeeResponseDto> getAllRestEmployees() {
        return restEmployeeResponseMapper.toResponseList(restEmployeeServicePort.getAllRestEmployees());
    }
}