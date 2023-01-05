package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.Station;
import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;

import java.util.List;

public interface VehicleDAO {

    /**
     * Get vehicle with the given id
     * @param regId - registration id of the vehicle
     * @return - vehicle that matches with the id
     */
    Vehicle getVehicleByRegId(String regId);

    /**
     * Add the vehicle
     * @param user - user creating the user
     * @param regId - registration id of the vehicle
     * @param type - type of vehicle - 2 wheeler or 4 wheeler etc
     * @param model - model of the vehicle
     * @return vehicle created
     */
    Vehicle add(User user, String regId, String type, String model);

    /**
     * Update price of the vehicle
     * @param vehicle - vehicle whose price to be updated
     * @param price - price per hour
     */
    void updatePrice(Vehicle vehicle, double price);

    /**
     * Update history of prices of the vehicle
     * @param vehicle - vehicle whose price to be updated
     * @param price - price per hour
     * @param user - admin who is updating the price
     */
    void updatePriceHistory(Vehicle vehicle, double price, User user);

    /**
     * Update the history of movement of vehicles across stations
     * @param vehicle vehicle being moved
     * @param station station to which the vehicle is moved
     * @param user user who is moving the vehicle
     */
    void updateStationHistory(Vehicle vehicle, Station station, User user);

    /**
     * Get the vehicles at a station based on available/all flag
     * @param station station which is given
     * @param availableOnly available/all flag
     * @return list of vehicles satisfying the condition
     */
    List<Vehicle> getVehiclesByStationAndAvailability(Station station, boolean availableOnly);

    /**
     * Update station
     * @param vehicle vehicle whose station to be updated
     * @param station station to which vehicle is moved
     */
    void updateStation(Vehicle vehicle, Station station);

    /**
     * Make the vehicle available/disable
     * @param vehicle Given vehicle
     * @param available flag to make vehicle available/disable
     */
    void makeAvailable(Vehicle vehicle, boolean available);

    /**
     * Get all vehicle
     * @return list of all vehicles
     */
    List<Vehicle> getAllVehicles();
    
}
