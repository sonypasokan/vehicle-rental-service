package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;

import java.net.URL;

public interface StationDAO {
    Station add(User user, String stationName, URL location);
}
