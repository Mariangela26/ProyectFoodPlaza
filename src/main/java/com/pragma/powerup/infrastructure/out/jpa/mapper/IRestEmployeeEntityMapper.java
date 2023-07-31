package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestEmployeeModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestEmployeeEntityMapper {

    RestEmployeeEntity toEntity(RestEmployeeModel restEmployeeModel);
    RestEmployeeModel toRestEmployeeModel(RestEmployeeEntity restEmployeeEntity);
    List<RestEmployeeModel> toRestEmployeeModelList(List<RestEmployeeEntity> restEmployeeEntityList);
}