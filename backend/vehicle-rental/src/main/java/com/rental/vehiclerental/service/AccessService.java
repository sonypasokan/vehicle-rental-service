package com.rental.vehiclerental.service;

import com.rental.vehiclerental.constants.OTPStrings;
import com.rental.vehiclerental.dao.PhoneDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.entity.PhoneOTP;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.SplittableRandom;

/**
 * Service handling all user access queries
 */
@Service
public class AccessService implements AccessEnabler {

    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Send OTP when user wants to login to the application
     * @param phone user's phone number
     * @throws EnvironmentVariableMissingException when expected environment
     * variables are not set in the system
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOtp(String phone) throws EnvironmentVariableMissingException {
        // Generate OTP
        String otp = generateOTP(OTPStrings.LENGTH_OF_OTP);
        System.out.println("OTP=" + otp);
        // send OTP
        sendOtp(phone, otp);
        // store OTP and phone number
        phoneDAO.save(phone, otp, OTPStrings.TTL_IN_MINUTES);
    }

    /**
     * Private method which internally calls Twilio services to send OTP to user's phone
     * @param phone user's phone
     * @param otp OTP to be sent to user
     * @throws EnvironmentVariableMissingException when expected environment
     * variables are not set in the system
     */
    private void sendOtp(String phone, String otp) throws EnvironmentVariableMissingException {
        String message = String.format(OTPStrings.OTP_MESSAGE, otp);
        String sid = new SMSService().send(message, phone);
        System.out.println("Message id: " + sid);
    }

    /**
     * Generate OTP which has to be shared with user
     * @param lengthOfOTP Length of the OTP
     * @return OTP which is generated of given length
     */
    private String generateOTP(int lengthOfOTP) {
        StringBuilder generatedOTP = new StringBuilder();
        SplittableRandom splittableRandom = new SplittableRandom();

        for (int i = 0; i < lengthOfOTP; i++) {
            int randomNumber = splittableRandom.nextInt(1, 9);
            generatedOTP.append(randomNumber);
        }
        return generatedOTP.toString();
    }

    /**
     * Validate the OTP which user entered against the one sent from the application
     * @param phone phone to which OTP was sent
     * @param inputOtp OTP input by the user
     * @return Pair of User object and a flag
     * User object will be present only if the validation was successful
     * Flag is to inform whether the user logged in for the first time or not
     * @throws IncorrectOTPException when the OTP entered does not match with what's sent to user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pair<User, Boolean> validateOtp(String phone, String inputOtp) throws IncorrectOTPException {
        PhoneOTP phoneOTP = phoneDAO.getLatestOtpByPhone(phone);
        if (!phoneOTP.getOtp().equals(inputOtp))
            throw new IncorrectOTPException("OTP does not match.");
        if (phoneOTP.getExpireTime().isBefore(LocalDateTime.now()))
            throw new IncorrectOTPException("OTP has expired.");
        User user = userDAO.getUserByPhone(phone);
        if (user != null)
            return Pair.of(user, true);
        user = userDAO.create(phone);
        return Pair.of(user, false);
    }

    /**
     * Create user's profile
     * @param userId user's id
     * @param name Name of the user
     * @param email Email id
     * @return User object which is created
     * @throws UserNotExistException when the given user id does not exist
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createProfile(int userId, String name, String email) throws UserNotExistException {
        User user = userDAO.verifyUser(userId);
        return userDAO.update(user, name, email);
    }

    /**
     * Make the given user an admin
     * @param userId User who has to be promoted as admin
     * @return User who is upgraded as admin
     * @throws UserNotExistException when the given user id does not exist
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User setAdmin(int userId) throws UserNotExistException {
        User user = userDAO.verifyUser(userId);
        user = userDAO.setAdmin(user);
        return user;
    }

}
