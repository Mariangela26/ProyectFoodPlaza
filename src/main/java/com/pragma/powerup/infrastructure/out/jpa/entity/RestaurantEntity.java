package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "resturant_id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nit", unique = true, nullable = false)
    private String nit;
    @Column(name = "addres", nullable = false)
    private String addres;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "urlLogo", nullable = false)
    private String urlLogo;
    @Column(name = "id_owner", nullable = false)
    private Long idOwner;

}
