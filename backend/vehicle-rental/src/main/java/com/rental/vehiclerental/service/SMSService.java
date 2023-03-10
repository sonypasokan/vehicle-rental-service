package com.rental.vehiclerental.service;

import com.rental.vehiclerental.exception.EnvironmentVariableMissingException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Twilio Messaging Service to phone
 */
@Component
@NoArgsConstructor
public class SMSService {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure

    private static String TWILIO_ACCOUNT_SID;
    private static String TWILIO_AUTH_TOKEN;
    private static String TWILIO_PHONE_NO;

    /**
     * Constructor collecting the set environment variables
     * @param twilioAccountSid Twilio Account ID
     * @param twilioAuthToken Twilio Auth Token
     * @param twilioFromPhone Twilio phone number from which the message to be sent
     */
    @Autowired
    public SMSService(@Value("${twilio.account-sid}") String twilioAccountSid,
                      @Value("${twilio.auth-token}") String twilioAuthToken,
                      @Value("${twilio.from-phone}") String twilioFromPhone) {
        TWILIO_ACCOUNT_SID = twilioAccountSid;
        TWILIO_AUTH_TOKEN = twilioAuthToken;
        TWILIO_PHONE_NO = twilioFromPhone;
    }

    /**
     * Send the message to the given phone
     * @param content Message content to be sent
     * @param toPhone To which phone the message to be sent
     * @return Message ID after successful messaging
     * @throws EnvironmentVariableMissingException when the required environment variables are not found
     */
    public String send(String content, String toPhone) throws EnvironmentVariableMissingException {
        if (TWILIO_ACCOUNT_SID == null) throw new EnvironmentVariableMissingException("TWILIO_ACCOUNT_SID");
        if (TWILIO_AUTH_TOKEN == null) throw new EnvironmentVariableMissingException("TWILIO_AUTH_TOKEN");
        if (TWILIO_PHONE_NO == null) throw new EnvironmentVariableMissingException("TWILIO_PHONE_NO");
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhone),
                new com.twilio.type.PhoneNumber(TWILIO_PHONE_NO),
                content)
                .create();
        return message.getSid();
    }
}