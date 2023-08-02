package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndState(Long id, String state);

    Page<OrderEntity> findByRestaurantIdAndState(Long id, String state, Pageable pageable);

    Boolean existsByIdAndState(Long id, String state);

    //List<OrderEntity> findByRestaurantIdAndState(Long id, String state, Pageable pageable);
}