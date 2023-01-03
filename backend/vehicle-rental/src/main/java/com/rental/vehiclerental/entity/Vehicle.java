package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Vehicle {

    @Id
    private String regId;

    private String type;

    private String model;

    private boolean isAvailable;

    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

}
