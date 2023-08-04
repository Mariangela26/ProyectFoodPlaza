package com.pragma.powerup.infrastructure.out.feingClients.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private String Identification;
    private String phone;
    private String email;
    private String pass;
    private RoleDto role;


}
