package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model storing all bookings
 */
@Data
@Entity
public class Booking {

    /**
     * Booking id (Primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * User making the booking
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * Vehicle being booked
     */
    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vehicle vehicle;

    /**
     * Station from where the booking is done
     */
    @JoinColumn(name = "from_station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Station fromStation;

    /**
     * Station from where the booking is ended
     */
    @JoinColumn(name = "to_station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Station toStation;

    /**
     * Booking time
     */
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fromTime;

    /**
     * Returning time
     */
    private LocalDateTime toTime;

    /**
     * Flag whether the vehicle is returned or not
     */
    private boolean isReturned;

    /**
     * Total amount for the booking
     */
    private double totalAmount;

}
