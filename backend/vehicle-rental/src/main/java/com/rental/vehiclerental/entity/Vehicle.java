package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Vehicle {

    @Id
    private String regId;

    private String type;

    private String model;

    private boolean isAvailable;

}
