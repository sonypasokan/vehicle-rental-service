package com.rental.vehiclerental.service;

import com.rental.vehiclerental.dao.StationDAO;
import com.rental.vehiclerental.dao.UserDAO;
import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Service handling station related operations
 */
@Service
public class StationService implements StationManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StationDAO stationDAO;

    /**
     * Add the station
     * @param userId User's id
     * @param stationName Name of station
     * @param location Map location of the station
     * @return Station which is added
     * @throws UserNotExistException when the user does not exist
     * @throws UserNotAdminException when the user is not an admin
     * @throws MalformedURLException when the location URL is found incorrect
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Station add(int userId, String stationName, String location)
            throws UserNotExistException, UserNotAdminException, MalformedURLException {
        User user = userDAO.verifyAdminUser(userId);
        URL url = null;
        if (!location.equals(""))
            url = new URL(location);
        return stationDAO.add(user, stationName, url);
    }

    /**
     * Get all stations in the DB
     * @return List of all stations
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Station> getAllStations() {
        return stationDAO.getAllStations();
    }
}
