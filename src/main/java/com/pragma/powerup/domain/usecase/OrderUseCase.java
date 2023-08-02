package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.model.orders.OrderDishRequestModel;
import com.pragma.powerup.domain.model.orders.OrderDishResponseModel;
import com.pragma.powerup.domain.model.orders.OrderRequestModel;
import com.pragma.powerup.domain.model.orders.OrderResponseModel;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.feingClients.IUserFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.IDishPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private  final IOrderPersistencePort orderPersistencePort;

    private final IToken token;

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IDishPersistencePort dishPersistencePort;

    private final IRestEmployeePersistencePort restaurantEmployeePersistencePort;

    private final IUserFeignClientPort userFeignClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IToken token, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IRestEmployeePersistencePort restEmployeePersistencePort, IUserFeignClientPort userFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.token = token;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantEmployeePersistencePort = restEmployeePersistencePort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public void saveOrder(OrderRequestModel orderRequestModel) {
        Date date = new Date();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idClientAuth = token.getUsuarioAutenticadoId(bearerToken);

        List<String> states = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");

        if(orderPersistencePort.existsByIdClientAndState(idClientAuth, states.get(0)) ||
                orderPersistencePort.existsByIdClientAndState(idClientAuth, states.get(1)) ||
                orderPersistencePort.existsByIdClientAndState(idClientAuth, states.get(2))) throw new ClientHasAnOrderException();

        Long idRestaurante = orderRequestModel.getRestaurantId();

        RestaurantModel restaurantModel= restaurantPersistencePort.getRestaurantById(idRestaurante);
        if(restaurantModel==null) throw new RestaurantNotExistException();
        OrderModel orderModel2 = new OrderModel(-1L, idClientAuth,date,"PENDIENTE",null,restaurantModel);

        List<OrderDishRequestModel> orderDishes = orderRequestModel.getDishes();
        if(orderDishes.isEmpty()){
            throw new NoDataFoundException();
        }
        for (int i=0; i<orderDishes.size();i++) {
            DishModel dishModel = dishPersistencePort.getDishById(orderDishes.get(i).getIdDish());
            if (dishModel == null) throw new DishNotExistException();
            if (dishModel.getRestaurantId().getId() != orderModel2.getRestaurant().getId()) throw new DishRestaurantIdNotIsEqualsOrderException();
            if(!dishModel.getActive()) throw new DishIsInactiveException();
        }
        OrderModel order =orderPersistencePort.saveOrder(orderModel2);


        List<OrderDishModel> orderDishesEmpty = new ArrayList<>();
        for (int i=0; i<orderDishes.size();i++){
            DishModel dishModel= dishPersistencePort.getDishById(orderDishes.get(i).getIdDish());
            OrderDishModel orderDish = new OrderDishModel(-1L, order,dishModel, String.valueOf(orderDishes.get(i).getQuantity()));
            orderDishesEmpty.add(orderDish);
        }

        orderPersistencePort.saveOrderDish(orderDishesEmpty);
    }

    @Override
    public List<OrderResponseModel> getAllOrdersWithPagination(Integer page, Integer size, String state) {
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByUserId(String.valueOf(idEmployeeAuth));

        List<OrderResponseModel> listOrdersResponse = new ArrayList<>();
        Long restaurantId = Long.parseLong(restaurantEmployeeModel.getRestaurantId());
        List<OrderModel> orders = orderPersistencePort.getAllOrdersWithPagination(page, size,restaurantId ,state);

        for (int i=0; i<orders.size();i++){
            OrderResponseModel orderResponseModel = new OrderResponseModel();
            orderResponseModel.setId(orders.get(i).getId());
            orderResponseModel.setIdClient(orders.get(i).getIdClient());
            if(orders.get(i).getEmployee()==null) orderResponseModel.setIdEmployee(null);
            else orderResponseModel.setIdEmployee(orders.get(i).getEmployee().getId());
            orderResponseModel.setDate(orders.get(i).getDate());
            orderResponseModel.setOrderDishes(new ArrayList<>());

            List<OrderDishModel>  orderDishes = orderPersistencePort.getAllOrdersByOrder(orders.get(i).getId());
            for (int k=0; k<orderDishes.size(); k++){
                DishModel dishModel= dishPersistencePort.getDishById(orderDishes.get(k).getDish().getId());
                OrderDishResponseModel orderDishResponseModel = new OrderDishResponseModel();
                orderDishResponseModel.setId(dishModel.getId());
                orderDishResponseModel.setName(dishModel.getName());
                orderDishResponseModel.setPrice(dishModel.getPrice());
                orderDishResponseModel.setDescription(dishModel.getDescription());
                orderDishResponseModel.setUrlImage(dishModel.getUrlImage());
                orderDishResponseModel.setCategoryId(dishModel.getCategoryId());
                orderDishResponseModel.setQuantity(orderDishes.get(k).getQuantity());

                orderResponseModel.getOrderDishes().add(orderDishResponseModel);
            }
            listOrdersResponse.add(orderResponseModel);
        }
        return listOrdersResponse;
    }

    @Override
    public void takeOrderAndUpdateStatus(Long idOrder, String estado) {
        if(!estado.equals("EN_PREPARACION")) throw new NoDataFoundException();
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndState(idOrder, "PENDIENTE"))) throw new NoDataFoundException();

        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByUserId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurant().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        orderModel.setEmployee(restaurantEmployeeModel);
        orderModel.setState(estado);

        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public void updateAndNotifyOrderReady(Long idOrder) {
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndState(idOrder, "EN_PREPARACION"))) throw new NoDataFoundException();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByUserId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurant().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        orderModel.setState("LISTO");
        orderPersistencePort.saveOrder(orderModel);

        UserModel userModel = userFeignClientPort.getUserById(orderModel.getIdClient());
        String nombreCliente = userModel.getName();
        String pin = validatePin(userModel);

    }

    @Override
    public void deliverOrder(Long idOrder, String pin) {
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndState(idOrder, "READY"))) throw new NoDataFoundException();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByUserId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurant().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        UserModel userModel = userFeignClientPort.getUserById(orderModel.getIdClient());
        String pin2 = validatePin(userModel);

        if(!(validatePin(userModel)).equals(pin) && !pin.equals(0)) throw new PinNotIsEqualsException();


        orderModel.setState("ENTREATING");
        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idClientAuth = token.getUsuarioAutenticadoId(bearerToken);

        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();
        Long idClientOrder = orderModel.getIdClient();

        if(idClientAuth!=idClientOrder) throw new ClientAuthMustBeEqualsClientOrderException();
        if(orderPersistencePort.existsByIdAndState(idOrder, "IN_PREPARATION")|| orderPersistencePort.existsByIdAndState(idOrder, "READY") ){
            throw new NoCancelOrdersPreparationOrReadyException();
        }else if(orderPersistencePort.existsByIdAndState(idOrder, "CANCELED")) throw new NoCancelOrderStatusCancelException();
        else if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndState(idOrder, "PENDENT"))) throw new OnlyCancelOrderStatusPendentException();


        orderModel.setState("CANCEL");
        orderPersistencePort.saveOrder(orderModel);
    }

    public String validatePin(UserModel userModel){
        String pinIdentification = userModel.getIdentification();
        String pinName = userModel.getName();
        String pinLastName= userModel.getLastName();
        String pin = pinName.substring(pinName.length()-2)+pinIdentification.substring(pinIdentification.length()-4)+pinLastName.substring(pinLastName.length()-2);
        return  pin;
    }



}

