package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {
    OrderModel saveOrder(OrderModel orderModel);

    void saveOrderDish(List<OrderDishModel> orderDishModels);

    Boolean existsByIdClientAndState(Long id, String state);

    List<OrderModel> getAllOrdersWithPagination(Integer page, Integer size, Long restaurantId, String state);

    List<OrderDishModel> getAllOrdersByOrder(Long orderId);

    OrderModel getOrderById(Long id);

    Boolean existsByIdAndState(Long id, String state);
}

