package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;

import java.util.List;

public interface VehicleDAO {

    Vehicle getVehicleByRegId(String regId);

    Vehicle add(User user, String regId, String type, String model);

    void updatePrice(Vehicle vehicle, double price, User user);

    void updateStationHistory(Vehicle vehicle, Station station, User user);

    List<Vehicle> getAvailableVehiclesByStation(Station station);

    void updateStation(Vehicle vehicle, Station station);

    void makeAvailable(Vehicle vehicle, boolean available);
}
