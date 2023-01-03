package com.rental.vehiclerental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.MandatoryFieldMissingException;
import com.rental.vehiclerental.service.StationManager;
import com.rental.vehiclerental.service.VehicleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            if(!payload.containsKey("stationId")) throw new MandatoryFieldMissingException("stationId");
            if(!payload.containsKey("price")) throw new MandatoryFieldMissingException("price");
            int userId = (int) payload.get("userId");
            int stationId = (int) payload.get("stationId");
            String type = payload.get("type").toString();
            String regId = payload.get("regId").toString();
            String model = payload.get("model").toString();
            double price = (double) payload.getOrDefault("price", 0);
            Vehicle vehicle = vehicleManager.add(userId, regId, stationId, type, model, price);
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

}
