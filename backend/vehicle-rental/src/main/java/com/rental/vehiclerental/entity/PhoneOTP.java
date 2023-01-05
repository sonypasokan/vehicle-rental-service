package com.rental.vehiclerental.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Model storing all the OTPs sent to the phones
 */
@Data
@Entity
public class PhoneOTP {

    /**
     * Id (Primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Phone number to which OTP was sent
     */
    private String phone;

    /**
     * OTP sent
     */
    private String otp;

    /**
     * Time at which OTP was saved in this table
     */
    private LocalDateTime updatedTime;

    /**
     * Expiry time
     */
    private LocalDateTime expireTime;

}
