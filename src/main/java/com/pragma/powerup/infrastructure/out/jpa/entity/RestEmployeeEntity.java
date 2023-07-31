package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee-restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestEmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "employee_restaurant_id", nullable = false)
    private Long id;

    @Column(name = "resturant_id", nullable = false)
    private String restaurantId;

    @Column(name = "employee_id", nullable = false)
    private String userId;
}
