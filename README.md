# Vehicle Rental Service
Application to book vehicles available at stations.

## Features

### Types of Users
1. Customers - booking vehicles
2. Admin - Making the resources available for customer

### Customer's features
- Login to app using phone number & OTP
- Fill profile details
- View all stations
- Select station to view all available vehicles
- Scan QR code in the vehicle, embedded with API to book the vehicle
- Return the vehicle at any station & see amount to pay
- View all bookings
- Upgrade as admin

### Admin's features
Features that are allowed only for admin are:
- View all vehicles at a station (including those not available)
- View all vehicles (including those not available)
- Add vehicle
- Add station
- Disable vehicle
- Move a vehicle to a station

## Technical Requirements

### Tech Stacks Used
1. Java 11
2. Maven 3.8.6
3. Spring boot 2.7.8
4. H2 Database

### Environment variables to set
Twilio API is used as SMS service for sending OTP to users
1. TWILIO_ACCOUNT_SID - Twilio Account SID
2. TWILIO_AUTH_TOKEN - Twilio Auth Token
3. TWILIO_PHONE_NO - Twilio phone number from which the OTP will be sent

## Technical Documentation
API documentation is available at swagger end point.
Code documentation is available as doc strings in the Java classes.

## Future Improvement
1. APIs can be added for features such as editing/renaming/deleting the vehicles/stations.
2. Application can be decoupled by using messaging services like Kafka to send OTP
3. Pagination for APIs with larger results.