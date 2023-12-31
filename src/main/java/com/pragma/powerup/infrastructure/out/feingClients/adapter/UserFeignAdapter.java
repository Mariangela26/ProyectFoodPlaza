package com.pragma.powerup.infrastructure.out.feingClients.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.feingClients.IUserFeignClientPort;
import com.pragma.powerup.infrastructure.out.feingClients.UserFeignClients;
import com.pragma.powerup.infrastructure.out.feingClients.dto.UserDto;
import com.pragma.powerup.infrastructure.out.feingClients.mapper.IUserDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFeignAdapter implements IUserFeignClientPort {

    private final UserFeignClients userFeignClients;

    private  final IUserDtoMapper userDtoMapper;

    @Override
    public Boolean existsUserById(Long usuarioId) {
        return userFeignClients.existsUserById(usuarioId);
    }

    @Override
    public UserModel getUserById(Long usuarioId) {
        UserDto userDto =userFeignClients.getUserById(usuarioId);
        return userDtoMapper.toUserModel(userDto);
    }

    @Override
    public UserModel getUserByEmail(String correo) {
        UserDto userDto= userFeignClients.getUserByEmail(correo);
        return userDtoMapper.toUserModel(userDto);
    }
}
