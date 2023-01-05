package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model storing history of all vehicle movement across stations
 */
@Data
@Entity
public class VehicleStationHistory {

    /**
     * Id (Primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Vehicle being moved
     */
    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    /**
     * The station to which the vehicle is moved
     */
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;

    /**
     * Time at which movement happened
     */
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    /**
     * User who made the movement
     */
    @JoinColumn(name = "updated_by_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
