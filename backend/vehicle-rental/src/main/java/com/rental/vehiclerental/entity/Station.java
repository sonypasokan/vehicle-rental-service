package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;
import java.net.URL;

import java.time.LocalDateTime;

/**
 * Model storing all stations information.
 */
@Data
@Entity
public class Station {

    /**
     * Station id (Primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Station's name
     */
    private String name;

    /**
     * Location of the station
     */
    private URL location;

    /**
     * Time at which new station was added
     */
    private LocalDateTime creationTime;

    /**
     * Admin who created the station
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

}
