package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.StationDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.dao.VehicleDAO;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.StationNotExistException;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;
import com.rental.vehiclerental.exception.VehicleNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VehicleService implements VehicleManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccessEnabler accessEnabler;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private StationDAO stationDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vehicle add(int userId, String regId, String type, String model, double price)
            throws UserNotExistException, UserNotAdminException, VehicleNotExistException, StationNotExistException {
        User user = accessEnabler.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle != null)
            throw new VehicleNotExistException("Vehicle already exists with this regId " + regId);
        vehicle = vehicleDAO.add(user, regId, type, model);
        vehicleDAO.updatePrice(vehicle, price, user);
        return vehicle;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(int userId, String regId, int stationId) throws UserNotExistException, UserNotAdminException, VehicleNotExistException, StationNotExistException {
        User user = accessEnabler.verifyAdminUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle not found with id " + regId);
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " does not exist");
        vehicleDAO.updateStationHistory(vehicle, station, user);
        vehicleDAO.updateStation(vehicle, station);
    }

    @Override
    public List<Vehicle> viewByStationId(int stationId) throws StationNotExistException {
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " does not exist");
        return vehicleDAO.getAvailableVehiclesByStation(station);
    }
}
