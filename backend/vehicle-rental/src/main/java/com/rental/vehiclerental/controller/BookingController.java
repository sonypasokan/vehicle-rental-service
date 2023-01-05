package com.rental.vehiclerental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.exception.MandatoryFieldMissingException;
import com.rental.vehiclerental.service.Bookable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * All booking related operations are managed.
 */
@RestController
@RequestMapping("/api/booking")
@Api(value = "Booking Controller")
public class BookingController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Bookable bookingService;

    /**
     * POST API that allows a user to book a vehicle.
     * This API needs to be embedded in the QR code of the vehicle.
     * @param payload Payload must contain -
     *                userId as integer and
     *                regId of the vehicle as String
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of booking
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Book the vehicle", response = ResponseEntity.class)
    @PostMapping("/add")
    public ResponseEntity<Object> bookVehicle(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("regId")) throw new MandatoryFieldMissingException("regId");
            int userId = (int) payload.get("userId");
            String regId = payload.get("regId").toString();
            Booking booking = bookingService.book(userId, regId);
            jsonObject.putPOJO("values", booking);
            jsonObject.put("success", true);
            jsonObject.put("message", "Successfully booked.");
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
     * GET API which lists all bookings done by the given user
     * @param userId userId as integer
     *
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of all bookings
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "View bookings", response = ResponseEntity.class)
    @GetMapping("/view/{userId}")
    public ResponseEntity<Object> viewBookings(@PathVariable int userId) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            List<Booking> bookings = bookingService.view(userId);
            jsonObject.putPOJO("values", bookings);
            jsonObject.put("success", true);
            jsonObject.put("message", "Successfully fetched bookings.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", "Unable to process your request - " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

    /**
     * POST API to return/cancel the booking done by the user
     * @param payload Payload must contain -
     *                bookingId as integer and
     *                stationId as integer - where the vehicle is going to be returned
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of booking
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Return vehicle", response = ResponseEntity.class)
    @PostMapping("/return")
    public ResponseEntity<Object> returnVehicle(@RequestBody Map<String, Object> payload){
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("bookingId")) throw new MandatoryFieldMissingException("bookingId");
            if(!payload.containsKey("stationId")) throw new MandatoryFieldMissingException("stationId");
            int bookingId = (int) payload.get("bookingId");
            int stationId = (int) payload.get("stationId");
            Booking booking = bookingService.returnVehicle(bookingId, stationId);
            jsonObject.putPOJO("values", booking);
            jsonObject.put("success", true);
            jsonObject.put("message", "Successfully fetched bookings.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", "Unable to process your request - " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }
}
