package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.RoleModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String Identification;
    private String phone;
    private String email;
    private String pass;
    private RoleModel role;
}
