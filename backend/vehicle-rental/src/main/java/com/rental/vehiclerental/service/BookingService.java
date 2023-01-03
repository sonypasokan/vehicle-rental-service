package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.BookingDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.dao.VehicleDAO;
import com.rental.vehiclerental.entity.Booking;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import com.rental.vehiclerental.exception.BookingNotExistException;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Booking book(int userId, int vehicleId) throws UserNotExistException, VehicleNotExistException {
        User user = userDAO.getUser(userId);
        // Check if the user exists
        if (user == null)
            throw new UserNotExistException("User with id " + userId + " does not exist");
        Vehicle vehicle = vehicleDAO.getVehicle(vehicleId);
        // Check if the vehicle exists
        if (vehicle == null)
            throw new VehicleNotExistException("Vehicle with id " + vehicleId + " does not exist");
        // Check if the vehicle is available
        if (!vehicle.isAvailable())
            throw new VehicleNotExistException("Vehicle with id " + vehicleId + " is not available for booking now");
        return bookingDAO.create(user, vehicle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Booking> view(int userId) throws UserNotExistException {
        User user = userDAO.getUser(userId);
        // Check if the user exists
        if (user == null)
            throw new UserNotExistException("User with id " + userId + " does not exist");
        return bookingDAO.getBookingByUser(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Booking returnVehicle(int bookingId) throws BookingNotExistException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null)
            throw new BookingNotExistException("Booking " + bookingId + " doesn't exist.");
        return bookingDAO.returnVehicle(booking);
    }

}
