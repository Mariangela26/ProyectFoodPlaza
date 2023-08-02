package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestEmployeeResponseDto;
import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestEmployeeResponseMapper {
    RestEmployeeResponseDto toResponse(RestEmployeeModel restaurantEmployeeModel);

    List<RestEmployeeResponseDto> toResponseList(List<RestEmployeeModel> restaurantEmployeeModelList);
}
