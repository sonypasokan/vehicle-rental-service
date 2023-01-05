package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model storing the history of price changes of all vehicles
 */
@Data
@Entity
public class VehiclePriceHistory {

    /**
     * Id (primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Vehicle whose price is updated
     */
    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    /**
     * Price per hour
     */
    private double pricePerHour;

    /**
     * Time at which this price was updated
     */
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    /**
     * Admin who updated the price
     */
    @JoinColumn(name = "updated_by_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

}
