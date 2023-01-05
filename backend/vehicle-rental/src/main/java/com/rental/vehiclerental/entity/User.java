package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Model storing all users (including admins)
 */
@Data
@Entity
@Table(name = "user_table")
public class User {

    /**
     * User id (Primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * User's name
     */
    private String name;

    /**
     * Email id
     */
    private String email;

    /**
     * Phone number
     */
    private String phone;

    /**
     * Flag whether the user has admin rights or not
     */
    private boolean isAdmin;

}
