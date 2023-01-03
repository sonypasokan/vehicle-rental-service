package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.User;
import com.rental.vehiclerental.entity.Vehicle;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class VehicleDAOImpl implements VehicleDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Vehicle getVehicle(int vehicleId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Vehicle vehicle;

        TypedQuery<Vehicle> query = currentSession.createQuery(
                "select a from Vehicle a where a.id=:vehicleId",
                Vehicle.class
        );
        query.setParameter("vehicleId", vehicleId);
        return query.getSingleResult();
    }

}
