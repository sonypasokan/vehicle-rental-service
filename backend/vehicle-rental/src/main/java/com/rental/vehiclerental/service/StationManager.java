package com.rental.vehiclerental.service;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.exception.UserNotAdminException;
import com.rental.vehiclerental.exception.UserNotExistException;

import java.net.MalformedURLException;
import java.net.URL;

public interface StationManager {
    Station add(int userId, String stationName, String location) throws UserNotExistException, UserNotAdminException, MalformedURLException;
}
