package com.rental.vehiclerental.entity;

import lombok.Data;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
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

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

}
