package com.pragma.powerup.domain.spi.feingClients;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserFeignClientPort {
    Boolean existsUserById(Long userId);

    UserModel getUserById(Long userId);

    UserModel getUserByEmail(String email);
}
