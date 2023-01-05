package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    private LocalDateTime createdTime;

    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Station station;
}
