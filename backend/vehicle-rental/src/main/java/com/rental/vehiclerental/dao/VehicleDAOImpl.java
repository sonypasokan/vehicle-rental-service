package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DB operations on Vehicle entity
 */
@Repository
public class VehicleDAOImpl implements VehicleDAO{

    @Autowired
    private EntityManager entityManager;

    /**
     * Get vehicle with the given id
     * @param regId - registration id of the vehicle
     * @return - vehicle that matches with the id
     */
    @Override
    public Vehicle getVehicleByRegId(String regId) {
        Session currentSession = entityManager.unwrap(Session.class);

        try {
            TypedQuery<Vehicle> query = currentSession.createQuery(
                    "select a from Vehicle a where a.regId=:regId",
                    Vehicle.class
            );
            query.setParameter("regId", regId);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Add the vehicle
     * @param user - user creating the user
     * @param regId - registration id of the vehicle
     * @param type - type of vehicle - 2 wheeler or 4 wheeler etc
     * @param model - model of the vehicle
     * @return vehicle created
     */
    @Override
    public Vehicle add(User user, String regId, String type, String model) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegId(regId);
        vehicle.setType(type);
        vehicle.setModel(model);
        vehicle.setAvailable(true);
        vehicle.setCreator(user);
        vehicle.setCreatedTime(LocalDateTime.now());
        entityManager.persist(vehicle);
        return vehicle;
    }

    /**
     * Update price of the vehicle
     * @param vehicle - vehicle whose price to be updated
     * @param price - price per hour
     */
    @Override
    public void updatePrice(Vehicle vehicle, double price) {
        vehicle.setPricePerHour(price);
        entityManager.persist(vehicle);
    }

    /**
     * Update history of prices of the vehicle
     * @param vehicle - vehicle whose price to be updated
     * @param price - price per hour
     * @param user - admin who is updating the price
     */
    @Override
    public void updatePriceHistory(Vehicle vehicle, double price, User user) {
        VehiclePriceHistory vehiclePriceHistory = new VehiclePriceHistory();
        vehiclePriceHistory.setVehicle(vehicle);
        vehiclePriceHistory.setPricePerHour(price);
        vehiclePriceHistory.setUpdatedTime(LocalDateTime.now());
        vehiclePriceHistory.setUpdatedBy(user);
        entityManager.persist(vehiclePriceHistory);
    }

    /**
     * Update the history of movement of vehicles across stations
     * @param vehicle vehicle being moved
     * @param station station to which the vehicle is moved
     * @param user user who is moving the vehicle
     */
    @Override
    public void updateStationHistory(Vehicle vehicle, Station station, User user) {
        VehicleStationHistory vehicleStationHistory = new VehicleStationHistory();
        vehicleStationHistory.setVehicle(vehicle);
        vehicleStationHistory.setStation(station);
        vehicleStationHistory.setUpdatedTime(LocalDateTime.now());
        vehicleStationHistory.setUpdatedBy(user);
        entityManager.persist(vehicleStationHistory);
    }

    /**
     * Get the vehicles at a station based on available/all flag
     * @param station station which is given
     * @param availableOnly available/all flag
     * @return list of vehicles satisfying the condition
     */
    @Override
    public List<Vehicle> getVehiclesByStationAndAvailability(Station station, boolean availableOnly) {
        Session currentSession = entityManager.unwrap(Session.class);

        StringBuilder queryString = new StringBuilder();
        queryString.append("select a from Vehicle a where a.station=:station ");
        if (availableOnly)
            queryString.append("and isAvailable=true");
        TypedQuery<Vehicle> query = currentSession.createQuery(
                queryString.toString(),
                Vehicle.class
        );
        query.setParameter("station", station);
        return query.getResultList();
    }

    /**
     * Update station
     * @param vehicle vehicle whose station to be updated
     * @param station station to which vehicle is moved
     */
    @Override
    public void updateStation(Vehicle vehicle, Station station) {
        vehicle.setStation(station);
        entityManager.persist(vehicle);
    }

    /**
     * Make the vehicle available/disable
     * @param vehicle Given vehicle
     * @param available flag to make vehicle available/disable
     */
    @Override
    public void makeAvailable(Vehicle vehicle, boolean available) {
        vehicle.setAvailable(available);
        entityManager.persist(vehicle);
    }

    /**
     * Get all vehicle
     * @return list of all vehicles
     */
    @Override
    public List<Vehicle> getAllVehicles() {
        Session currentSession = entityManager.unwrap(Session.class);

        TypedQuery<Vehicle> query = currentSession.createQuery(
                "select a from Vehicle a",
                Vehicle.class
        );
        return query.getResultList();
    }

}
