package com.rental.vehiclerental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.exception.MandatoryFieldMissingException;
import com.rental.vehiclerental.service.StationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/api/station")
@Api(value = "Station Controller")
public class StationController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StationManager stationManager;

    @ApiOperation(value = "Add Station", response = ResponseEntity.class)
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Map<String, Object> payload) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        HttpStatus status;

        try {
            if(!payload.containsKey("userId")) throw new MandatoryFieldMissingException("userId");
            if(!payload.containsKey("stationName")) throw new MandatoryFieldMissingException("stationName");
            int userId = (int) payload.get("userId");
            String stationName = payload.get("stationName").toString();
            String location = payload.getOrDefault("location", "").toString();
            Station station = stationManager.add(userId, stationName, location);
            jsonObject.putPOJO("values", station);
            jsonObject.put("success", true);
            jsonObject.put("message", "New station added.");
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
