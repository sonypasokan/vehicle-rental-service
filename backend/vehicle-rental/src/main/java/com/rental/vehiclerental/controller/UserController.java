package com.rental.vehiclerental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.MandatoryFieldMissingException;
import com.rental.vehiclerental.service.AccessEnabler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all user/admin operations
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "User controller")
public class UserController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessEnabler accessEnabler;

    /**
     * POST API to send OTP to the user.
     * When a user/admin tries to use the app, they have to authenticate through their phone.
     * Once the phone number is given, an OTP will be sent to the user using Twilio SMS service.
     * @param payload must contain
     *                phone - with pin code in String format
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Send OTP", response = ResponseEntity.class)
    @PostMapping("/send-otp")
    public ResponseEntity<Object> sendOtp(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if (!payload.containsKey("phone")) throw new MandatoryFieldMissingException("phone");
            String phone = payload.get("phone").toString();
            accessEnabler.sendOtp(phone);
            jsonObject.put("success", true);
            jsonObject.put("message", "Successfully sent the OTP.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("success", false);
            jsonObject.put("message", "Unable to process your request - " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

    /**
     * POST API to login to the app using phone & OTP received
     * A new account will be created if it's first time.
     * Otherwise existing account will be loaded.
     * @param payload must contain
     *                phone - with pin code as String
     *                OTP - received through SMS
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of the user and a flag saying whether it's a new user or not.
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Login using phone & OTP", response = ResponseEntity.class)
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if (!payload.containsKey("phone")) throw new MandatoryFieldMissingException("phone");
            if (!payload.containsKey("otp")) throw new MandatoryFieldMissingException("otp");
            String phone = payload.get("phone").toString();
            String otp = payload.get("otp").toString();
            Pair<User, Boolean> userStatus = accessEnabler.validateOtp(phone, otp);
            Map<String, Object> values = new HashMap<>();
            values.put("user", userStatus.getFirst());
            values.put("exists", userStatus.getSecond());
            jsonObject.put("success", true);
            jsonObject.putPOJO("values", values);
            jsonObject.put("message", "User logged in successfully.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

    /**
     * POST API to signup the user by creating/updating profile
     * @param payload must contain
     *                userId - in integer
     *                name - String
     *                email - String
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of the account created
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Profile creation", response = ResponseEntity.class)
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if (!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if (!payload.containsKey("name")) throw new MandatoryFieldMissingException("name");
            if (!payload.containsKey("email")) throw new MandatoryFieldMissingException("email");
            int userId = (int) payload.get("userId");
            String name = payload.get("name").toString();
            String email = payload.get("email").toString();
            User user = accessEnabler.createProfile(userId, name, email);
            jsonObject.put("success", true);
            jsonObject.putPOJO("values", user);
            jsonObject.put("message", "Successfully created the profile.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

    /**
     * POST API to make the given user as admin
     * @param payload must contain -
     *                userId - in integer
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of the user
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Mark existing user as admin", response = ResponseEntity.class)
    @PostMapping("/admin/add")
    public ResponseEntity<Object> addAdmin(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if (!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            int userId = (int) payload.get("userId");
            User user = accessEnabler.setAdmin(userId);
            jsonObject.put("success", true);
            jsonObject.putPOJO("values", user);
            jsonObject.put("message", "Successfully added " + userId + " as admin.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }


}
