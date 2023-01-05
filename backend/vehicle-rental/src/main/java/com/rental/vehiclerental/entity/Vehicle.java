package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model storing all vehicle's information
 */
@Data
@Entity
@Table
public class Vehicle {

    /**
     * Registration Id of the vehicle (Primary key)
     */
    @Id
    private String regId;

    /**
     * Type of the vehicle - 2 wheeler/4 wheeler etc
     */
    private String type;

    /**
     * Vehicle model
     */
    private String model;

    /**
     * Flag whether the vehicle is available for booking
     */
    private boolean isAvailable;

    /**
     * Admin who added the vehicle
     */
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    /**
     * Time at which vehicles was added to the inventory
     */
    private LocalDateTime createdTime;

    /**
     * Station at which the vehicle is available
     */
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Station station;

    /**
     * Price per hour
     */
    private double pricePerHour;
}
