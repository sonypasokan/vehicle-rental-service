package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.*;

import java.util.List;


public interface VehicleManager {

    Vehicle add(int userId, String regId, String type, String model, double price)
            throws UserNotExistException, UserNotAdminException,
            VehicleNotExistException, StationNotExistException;

    void move(int userId, String regId, int stationId)
            throws UserNotExistException, UserNotAdminException,
            VehicleNotExistException, StationNotExistException;

    List<Vehicle> viewAllByStationId(int stationId) throws StationNotExistException;

    List<Vehicle> viewAvailableByStationId(int stationId) throws StationNotExistException;

    Vehicle disable(int userId, String regId)
            throws UserNotExistException, UserNotAdminException,
            VehicleNotExistException, BookingNotExistException;

    List<Vehicle> viewAll();

    Vehicle updatePrice(int userId, String regId, double price)
            throws UserNotExistException, UserNotAdminException, VehicleNotExistException;

}
