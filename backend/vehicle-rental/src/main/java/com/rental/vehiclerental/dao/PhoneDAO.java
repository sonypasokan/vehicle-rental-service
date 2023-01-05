package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.PhoneOTP;


public interface PhoneDAO {

    /**
     * Get the latest OTP sent to the phone.
     * @param phone - phone no
     * @return PhoneOTP which is sent latest
     */
    PhoneOTP getLatestOtpByPhone(String phone);

    /**
     * Save the newly sent phone OTP in DB.
     * @param phone - to which OTP was sent
     * @param otp - OTP which was sent
     * @param minutesToLive - TTL using which the expiry time can be calculated
     */
    void save(String phone, String otp, int minutesToLive);
}
