package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.BookingDAO;
import com.rental.vehiclerental.dao.StationDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.dao.VehicleDAO;
import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.BookingNotExistException;
import com.rental.vehiclerental.exception.StationNotExistException;
import com.rental.vehiclerental.exception.UserNotExistException;
import com.rental.vehiclerental.exception.VehicleNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService implements Bookable {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private StationDAO stationDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Booking book(int userId, String regId) throws UserNotExistException, VehicleNotExistException {
        User user = userDAO.verifyUser(userId);
        Vehicle vehicle = vehicleDAO.getVehicleByRegId(regId);
        // Check if the vehicle exists
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle with id " + regId + " does not exist");
        // Check if the vehicle is available
        if (!vehicle.isAvailable())
            throw new VehicleNotExistException("Vehicle with id " + regId + " is not available for booking now");
        if (vehicle.getStation() == null)
            throw new VehicleNotExistException("Vehicle is not available in any station");
        vehicleDAO.makeAvailable(vehicle, false);
        return bookingDAO.create(user, vehicle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Booking> view(int userId) throws UserNotExistException {
        User user = userDAO.verifyUser(userId);
        return bookingDAO.getBookingByUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Booking returnVehicle(int bookingId, int stationId) throws BookingNotExistException, StationNotExistException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null)
            throw new BookingNotExistException("Booking " + bookingId + " doesn't exist.");
        Station station = stationDAO.getStation(stationId);
        if (station == null)
            throw new StationNotExistException("Station " + stationId + " doesn't exist.");
        vehicleDAO.makeAvailable(booking.getVehicle(), true);
        vehicleDAO.updateStation(booking.getVehicle(), station);
        return bookingDAO.returnVehicle(booking, station);
    }

}
