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

@RestController
@RequestMapping("/api/vehicle")
@Api(value = "Vehicle Controller")
public class VehicleController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleManager vehicleManager;

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

}
