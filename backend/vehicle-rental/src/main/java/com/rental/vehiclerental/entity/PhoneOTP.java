package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class PhoneOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String phone;

    private String otp;

    private LocalDateTime updatedTime;

    private LocalDateTime expireTime;

}
