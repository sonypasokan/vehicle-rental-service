package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.StationDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.dao.VehicleDAO;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service handling all vehicle related operations
 */
@Service
public class VehicleService implements VehicleManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private StationDAO stationDAO;

    /**
     * Add vehicle
     * @param userId Admin's id
     * @param regId Registration id of the vehicle
     * @param type Type of the vehicle
     * @param model Model
     * @param price Price per hour
     * @return Vehicle which is added
     * @throws UserNotExistException when the user does not exist
     * @throws UserNotAdminException when the user is not an admin
     * @throws VehicleNotExistException when the vehicle already exist with given regId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vehicle add(int userId, String regId, String type, String model, double price)
            throws UserNotExistException, UserNotAdminException, VehicleNotExistException {
        User user = userDAO.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle != null)
            throw new VehicleNotExistException("Vehicle already exists with this regId " + regId);
        vehicle = vehicleDAO.add(user, regId, type, model);
        vehicleDAO.updatePrice(vehicle, price);
        vehicleDAO.updatePriceHistory(vehicle, price, user);
        return vehicle;
    }

    /**
     * Move the vehicle to a station
     * @param userId Admin making the movement
     * @param regId Reg ID of the vehicle
     * @param stationId To which station the vehicle is moved
     * @throws UserNotExistException when the user does not exist
     * @throws UserNotAdminException when the user is not an admin
     * @throws VehicleNotExistException when the vehicle does not exist
     * @throws StationNotExistException when the station does not exist
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(int userId, String regId, int stationId)
            throws UserNotExistException, UserNotAdminException,
            VehicleNotExistException, StationNotExistException {
        User user = userDAO.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle not found with id " + regId);
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " does not exist");
        if (!vehicle.isAvailable())
            throw new VehicleNotExistException("Vehicle is not available - (either already disabled or in use)");
        vehicleDAO.updateStationHistory(vehicle, station, user);
        vehicleDAO.updateStation(vehicle, station);
    }

    /**
     * Get all vehicles at the given station ID
     * @param stationId Station's ID
     * @return List of vehicles
     * @throws StationNotExistException when the station does not exist
     */
    @Override
    public List<Vehicle> viewAllByStationId(int stationId) throws StationNotExistException {
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " does not exist");
        return vehicleDAO.getVehiclesByStationAndAvailability(station, false);
    }

    /**
     * Get all available vehicles at the given station ID
     * @param stationId Station's ID
     * @return List of available vehicles
     * @throws StationNotExistException when the station does not exist
     */
    @Override
    public List<Vehicle> viewAvailableByStationId(int stationId) throws StationNotExistException {
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " does not exist");
        return vehicleDAO.getVehiclesByStationAndAvailability(station, true);
    }

    /**
     * Disable a vehicle so that it's not available for booking anymore
     * @param userId Admin's id
     * @param regId Reg ID of vehicle
     * @return Vehicle after disabling
     * @throws UserNotExistException when the user does not exist
     * @throws UserNotAdminException when the user is not an admin
     * @throws VehicleNotExistException when the vehicle is not available
     * because it's either in use or it's already disabled
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vehicle disable(int userId, String regId)
            throws UserNotExistException, UserNotAdminException,
            VehicleNotExistException {
        User user = userDAO.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle not found with id " + regId);
        if (!vehicle.isAvailable())
            throw new VehicleNotExistException("Vehicle is not available - (either already disabled or in use)");
        vehicleDAO.makeAvailable(vehicle, false);
        return vehicle;
    }

    /**
     * Get all vehicles
     * @return List of vehicles
     */
    @Override
    public List<Vehicle> viewAll() {
        return vehicleDAO.getAllVehicles();
    }

    /**
     * Update price of the vehicle
     * @param userId Admin's id
     * @param regId Reg ID of the vehicle
     * @param price Price per hour
     * @return Vehicle after updating price
     * @throws UserNotExistException when user is not found
     * @throws UserNotAdminException when user is not not an admin
     * @throws VehicleNotExistException when the vehicle is not available for this change
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vehicle updatePrice(int userId, String regId, double price)
            throws UserNotExistException, UserNotAdminException, VehicleNotExistException{
        User user = userDAO.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle not found with id " + regId);
        if (!vehicle.isAvailable())
            throw new VehicleNotExistException("Vehicle is not available - (either already disabled or in use)");
        vehicleDAO.updatePriceHistory(vehicle, price, user);
        vehicleDAO.updatePrice(vehicle, price);
        return vehicle;
    }
}
