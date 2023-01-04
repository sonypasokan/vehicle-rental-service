package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class VehicleStationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Station station;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @JoinColumn(name = "updated_by_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
