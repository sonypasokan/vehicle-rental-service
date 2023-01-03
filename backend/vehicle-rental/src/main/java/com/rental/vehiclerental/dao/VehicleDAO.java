package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;

public interface VehicleDAO {

    Vehicle getVehicleByRegId(String regId);

    Vehicle add(User user, String regId, String type, String model);

    void updatePrice(Vehicle vehicle, double price, User user);

    void updateStation(Vehicle vehicle, Station station, User user);
}
