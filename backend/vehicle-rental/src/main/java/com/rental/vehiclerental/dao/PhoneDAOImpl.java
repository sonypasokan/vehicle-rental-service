package com.rental.vehiclerental.dao;

import com.rental.vehiclerental.entity.PhoneOTP;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

/**
 * DB operations on PhoneOTP entity.
 */
@Repository
public class PhoneDAOImpl implements PhoneDAO {

    @Autowired
    private EntityManager entityManager;

    /**
     * Get the latest OTP sent to the phone.
     * @param phone - phone no
     * @return PhoneOTP which is sent latest
     */
    @Override
    public PhoneOTP getLatestOtpByPhone(String phone) {
        Session currentSession = entityManager.unwrap(Session.class);

        TypedQuery<PhoneOTP> query = currentSession.createQuery(
                "select a from PhoneOTP a where a.phone=:phone " +
                        "order by updatedTime desc",
                PhoneOTP.class
        );
        query.setParameter("phone", phone);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    /**
     * Save the newly sent phone OTP in DB.
     * @param phone - to which OTP was sent
     * @param otp - OTP which was sent
     * @param minutesToLive - TTL using which the expiry time can be calculated
     */
    @Override
    public void save(String phone, String otp, int minutesToLive) {
        PhoneOTP phoneOTP = new PhoneOTP();
        phoneOTP.setPhone(phone);
        phoneOTP.setOtp(otp);
        phoneOTP.setUpdatedTime(LocalDateTime.now());
        phoneOTP.setExpireTime(LocalDateTime.now().plusMinutes(minutesToLive));
        entityManager.persist(phoneOTP);
   }
}
