package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestEmployeeRepository extends JpaRepository<RestEmployeeEntity, Long> {

    Optional<RestEmployeeEntity> findByUserId(String idEmployee);

}