package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.feingClients.IUserFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.out.feingClients.adapter.UserFeignAdapter;
import com.pragma.powerup.infrastructure.out.feingClients.UserFeignClients;
import com.pragma.powerup.infrastructure.out.feingClients.mapper.IUserDtoMapper;
import com.pragma.powerup.infrastructure.out.jpa.adapter.*;
import com.pragma.powerup.infrastructure.out.jpa.mapper.*;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import com.pragma.powerup.infrastructure.out.token.TokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;

    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;


    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    private final UserFeignClients userFeignClient;
    private final IUserDtoMapper userDtoMapper;

    private  final IRestEmployeeRepository restaurantEmployeeRepository;
    private final IRestEmployeeEntityMapper restaurantEmployeeEntityMapper;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private  final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserFeignClientPort userFeignClientPort(){
        return new UserFeignAdapter(userFeignClient, userDtoMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){

        return new RestaurantUseCase(restaurantPersistencePort(), userFeignClientPort());
    }

    @Bean
    public IToken token(){
        return new TokenAdapter();
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){

        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IDishServicePort dishServicePort(){

        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort(), token(), categoryServicePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IRestEmployeePersistencePort restaurantEmployeePersistencePort(){
        return new RestEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IRestEmployeeServicePort restaurantEmployeeServicePort(){
        return new RestEmployeeUseCase(restaurantEmployeePersistencePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), token(), restaurantPersistencePort(), dishPersistencePort(), restaurantEmployeePersistencePort(), userFeignClientPort());
    }

}