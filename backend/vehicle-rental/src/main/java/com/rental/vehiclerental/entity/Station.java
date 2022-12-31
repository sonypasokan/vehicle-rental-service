package com.rental.vehiclerental.entity;

import lombok.Data;
import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URL;

import java.time.LocalDateTime;

@Data
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private URL location;

    private LocalDateTime creationTime;

}
