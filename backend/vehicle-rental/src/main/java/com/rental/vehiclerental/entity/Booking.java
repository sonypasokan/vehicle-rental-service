package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @JoinColumn(name = "from_station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station fromStation;

    @JoinColumn(name = "to_station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station toStation;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private boolean isReturned;

    private float totalAmount;

}
