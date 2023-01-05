package com.rental.vehiclerental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.MandatoryFieldMissingException;
import com.rental.vehiclerental.service.VehicleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Manages all vehicle operations
 */
@RestController
@RequestMapping("/api/vehicle")
@Api(value = "Vehicle Controller")
public class VehicleController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleManager vehicleManager;

    /**
     * POST API to add a vehicle.
     * Restricted to admin.
     * @param payload must contain
     *                userId - admin's id as integer
     *                type - of the vehicle in String
     *                regId - registration id of the vehicle in String
     *                model - String
     *                price - per hour as double value
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of the vehicle added
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Add Vehicle", response = ResponseEntity.class)
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("type")) throw new MandatoryFieldMissingException("type");
            if(!payload.containsKey("regId")) throw new MandatoryFieldMissingException("regId");
            if(!payload.containsKey("model")) throw new MandatoryFieldMissingException("model");
            if(!payload.containsKey("price")) throw new MandatoryFieldMissingException("price");
            int userId = (int) payload.get("userId");
            String type = payload.get("type").toString();
            String regId = payload.get("regId").toString();
            String model = payload.get("model").toString();
            double price = (double) payload.getOrDefault("price", 0);
            Vehicle vehicle = vehicleManager.add(userId, regId, type, model, price);
            jsonObject.putPOJO("values", vehicle);
            jsonObject.put("success", true);
            jsonObject.put("message", "New vehicle added.");
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
     * POST API to move vehicle to a station
     * Restricted to admins.
     * @param payload must contain
     *                userId - admin's id
     *                regId - vehicle getting moved
     *                stationId - to which station
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Move Vehicle", response = ResponseEntity.class)
    @PostMapping("/move")
    public ResponseEntity<Object> move(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("regId")) throw new MandatoryFieldMissingException("regId");
            if(!payload.containsKey("stationId")) throw new MandatoryFieldMissingException("stationId");
            int userId = (int) payload.get("userId");
            int stationId = (int) payload.get("stationId");
            String regId = payload.get("regId").toString();
            vehicleManager.move(userId, regId, stationId);
            jsonObject.put("success", true);
            jsonObject.put("message", "Vehicle moved successfully");
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
     * GET API to view all vehicles at a given station
     * irrespective of whether the vehicle is available to use or not.
     * A vehicle used by a user and started from the given station will also be listed.
     * But those vehicles will be marked as not available.
     * @param stationId Station which vehicles will be listed
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of all vehicles
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Get all vehicles at station", response = ResponseEntity.class)
    @GetMapping("/view/all/{stationId}")
    public ResponseEntity<Object> viewAllAtStation(@PathVariable int stationId) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            List<Vehicle> vehicleList = vehicleManager.viewAllByStationId(stationId);
            jsonObject.putPOJO("values", vehicleList);
            jsonObject.put("success", true);
            jsonObject.put("message", "Vehicle listed successfully");
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
     * GET API to view only available vehicles at a given station
     * A vehicle used by a user or a vehicle disabled by admin wont be listed.
     * @param stationId Station which vehicles will be listed
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of available vehicles at the station
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Get available vehicles at station", response = ResponseEntity.class)
    @GetMapping("/view/available/{stationId}")
    public ResponseEntity<Object> viewAvailableAtStation(@PathVariable int stationId) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            List<Vehicle> vehicleList = vehicleManager.viewAvailableByStationId(stationId);
            jsonObject.putPOJO("values", vehicleList);
            jsonObject.put("success", true);
            jsonObject.put("message", "Vehicle listed successfully");
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
     * POST API to disable a vehicle so that no user can use it.
     * Restricted only to admin.
     * @param userId - admin's id
     * @param regId - registration id of the vehicle being marked
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of the vehicle disabled
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "Make the vehicle unavailable", response = ResponseEntity.class)
    @PostMapping("/disable/{userId}/{regId}")
    public ResponseEntity<Object> disable(@PathVariable int userId, @PathVariable String regId) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            Vehicle vehicle = vehicleManager.disable(userId, regId);
            jsonObject.putPOJO("values", vehicle);
            jsonObject.put("success", true);
            jsonObject.put("message", "Vehicle " + regId + " is no more available");
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
     * GET API to view all vehicles
     * irrespective of whether the vehicle is yet to be moved to a station or not.
     * A vehicle used by a user or disabled by the admin will also be listed.
     * But those vehicles will be marked as not available.
     *
     * @return Response contains
     * boolean success - true/false saying whether the API executed successfully
     * String message - Detailed response
     * Map values - containing the details of all vehicles
     *
     * Response status:
     * 200 - when success
     * 400 - when failure
     */
    @ApiOperation(value = "View All Vehicles", response = ResponseEntity.class)
    @GetMapping("/view")
    public ResponseEntity<Object> view() {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            List<Vehicle> vehicleList = vehicleManager.viewAll();
            jsonObject.putPOJO("values", vehicleList);
            jsonObject.put("success", true);
            jsonObject.put("message", "Vehicles listed successfully");
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
     * POST API to update price of the vehicle for booking.
     * Restricted to admin.
     * @param payload must contain
     *                regId - Vehicle ID
     *                userId - Admin's ID
     * @return The vehicle which price is updated
     */
    @ApiOperation(value = "Update price of the vehicle", response = ResponseEntity.class)
    @PostMapping("/update/price")
    public ResponseEntity<Object> updatePrice(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("regId")) throw new MandatoryFieldMissingException("regId");
            if(!payload.containsKey("price")) throw new MandatoryFieldMissingException("price");
            String regId = payload.get("regId").toString();
            int userId = (int) payload.get("userId");
            double price = (double) payload.get("price");
            Vehicle vehicle = vehicleManager.updatePrice(userId, regId, price);
            jsonObject.putPOJO("values", vehicle);
            jsonObject.put("success", true);
            jsonObject.put("message", "Price updated successfully");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("success", false);
            jsonObject.put("message", "Unable to process your request - " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jsonObject, status);
    }

}
