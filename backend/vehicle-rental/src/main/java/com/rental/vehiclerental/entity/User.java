package com.rental.vehiclerental.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_table")
public class User {

    @Id
    private int id;

    private String username;

    private String name;

    private String email;

    private String phone;

    private boolean isAdmin;

}
