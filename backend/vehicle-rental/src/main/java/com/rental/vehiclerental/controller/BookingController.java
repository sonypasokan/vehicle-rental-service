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

@RestController
@RequestMapping("/api/booking")
@Api(value = "Booking Controller")
public class BookingController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Bookable bookingService;

    @ApiOperation(value = "Book the vehicle", response = ResponseEntity.class)
    @PostMapping("/add")
    public ResponseEntity<Object> bookVehicle(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId"))
                throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("vehicleId"))
                throw new MandatoryFieldMissingException("vehicleId");
            int userId = (int) payload.get("userId");
            int vehicleId = (int) payload.get("vehicleId");
            Booking booking = bookingService.book(userId, vehicleId);
            jsonObject.putPOJO("values", booking);
            jsonObject.put("success", true);
            jsonObject.put("message", "Successfully booked.");
            status = HttpStatus.OK;
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", "Unable to process your request - " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

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

    @ApiOperation(value = "Return vehicle", response = ResponseEntity.class)
    @PostMapping("/return")
    public ResponseEntity<Object> returnVehicle(@RequestBody Map<String, Object> payload){
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("bookingId"))
                throw new MandatoryFieldMissingException("bookingId");
            int bookingId = (int) payload.get("bookingId");
            Booking booking = bookingService.returnVehicle(bookingId);
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
