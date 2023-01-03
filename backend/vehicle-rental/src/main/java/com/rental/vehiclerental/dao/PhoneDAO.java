package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.PhoneOTP;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface PhoneDAO {
    PhoneOTP getLatestOtpByPhone(String phone);

    void save(String phone, String otp, int minutesToLive);
}
