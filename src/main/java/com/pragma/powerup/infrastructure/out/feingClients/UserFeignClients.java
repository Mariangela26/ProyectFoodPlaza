package com.pragma.powerup.infrastructure.out.feingClients;

import com.pragma.powerup.infrastructure.out.feingClients.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "localhost:8081/api/v1/user")
public interface UserFeignClients {

    @GetMapping("/existsUserById/{id}")
    Boolean existsUserById(@PathVariable(value = "id") Long userId);

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable(value = "id") Long userId);

    @GetMapping("/email/{email}")
    UserDto getUserByEmail(@PathVariable(value = "email") String email);
}