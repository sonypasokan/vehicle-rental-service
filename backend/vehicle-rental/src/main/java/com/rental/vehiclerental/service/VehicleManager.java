package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.StationNotExistException;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;
import com.rental.vehiclerental.exception.VehicleNotExistException;


public interface VehicleManager {

    Vehicle add(int userId, String regId, String type, String model, double price) throws UserNotExistException, UserNotAdminException, VehicleNotExistException, StationNotExistException;

    void move(int userId, String regId, int stationId) throws UserNotExistException, UserNotAdminException, VehicleNotExistException, StationNotExistException;
}
