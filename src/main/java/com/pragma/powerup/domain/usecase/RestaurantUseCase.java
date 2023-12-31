package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.UserDoesNotOwnerException;
import com.pragma.powerup.domain.exception.UserNotExistException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.feingClients.IUserFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private  final IRestaurantPersistencePort restaurantPersistencePort;

    private  final IUserFeignClientPort userFeignClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        boolean existUser = userFeignClient.existsUserById(restaurantModel.getIdOwner());
        //System.out.println(existUser);
        if (!existUser) throw new UserNotExistException();
        UserModel user = userFeignClient.getUserById(restaurantModel.getIdOwner());
        //System.out.println(user.getRol());
        if (user.getRole().getId() != 2) throw new UserDoesNotOwnerException();

        RestaurantModel restaurantModel2 =restaurantPersistencePort.getRestaurantByIdPropietario(user.getId());
        //  System.out.println("Es un propietario");
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        return restaurantPersistencePort.getRestaurantById(id);
    }

    @Override
    public RestaurantModel getRestaurantByIdOwner(Long id_propietario) {
        return restaurantPersistencePort.getRestaurantByIdPropietario(id_propietario);
    }

    @Override
    public List<RestaurantModel> getAllRestaurants() {
        return restaurantPersistencePort.getAllRestaurants();
    }

    @Override
    public List<RestaurantModel> getRestaurantWithPagination(Integer page, Integer size) {
        //  if(page>=0) page--;
        return restaurantPersistencePort.getRestaurantsWithPagination(page,size);
    }

    @Override
    public void deleteRestaurantById(Long id) {
        restaurantPersistencePort.deleteRestaurantById(id);
    }
}
