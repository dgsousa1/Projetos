package lapr.project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.utils.SendMail;

/**
 *
 *
 */
public class LockVehicleController {

    /**
     * Park manager.
     */
    ParkRegistration parkRegistration;
    
    /**
     * User manager.
     */
    UserRegistration userRegistration;
    
    /**
     * Trip manager
     */
    TripRegistration tripRegistration;
    
    /**
     * A service that provides sending mails.
     */
    SendMail mailService;

    /**
     * 
     */
    public static final String PAM_ERROR = "One or more parameters are empty";

    /**
     * Builder responsible for getting the lock vehicles being used.
     * @param parkRegistration
     * @param userRegistration
     * @param tripRegistration 
     */
    public LockVehicleController(ParkRegistration parkRegistration, UserRegistration userRegistration, TripRegistration tripRegistration) {
        this.parkRegistration = parkRegistration;
        this.userRegistration = userRegistration;
        this.tripRegistration = tripRegistration;
        this.mailService = new SendMail();

    }

    /**
     * Lock a specific escooter at a park.
     *
     * Basic: Lock a specific escooter at a park.
     * Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param escooterDescription Escooter to lock.
     * @param parkIdentification The Park Identification.
     * @param username User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     */
    public long lockEscooter(String escooterDescription, String parkIdentification,
            String username) {
        if (escooterDescription.isEmpty() || parkIdentification.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = userRegistration.lockVehicleParkID(escooterDescription, parkIdentification);
            float cost = calculateCost(username);
            userRegistration.addInvoiceLine(username, Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR), cost);
            mailService.emailBuilder(userRegistration.getClientEmail(username), escooterDescription, parkIdentification, username);
            return time;
        } catch (Exception ex) {
            return 0;
        }
    }

   /**
     * Lock a specific bicycle at a park.
     *
     * Basic: Lock a specific bicycle at a park.
     * Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param bicycleDescription Bicycle to lock.
     * @param parkIdentification The Park Identification.
     * @param username User that requested the unlock.
     * @return The time in milliseconds at which the bicycle was locked.
     */
    public long lockBicycle(String bicycleDescription, String parkIdentification,
            String username) {
        if (bicycleDescription.isEmpty() || parkIdentification.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = userRegistration.lockVehicleParkID(bicycleDescription, parkIdentification);
            float cost = calculateCost(username);
            userRegistration.addInvoiceLine(username, Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR), cost);
            mailService.emailBuilder(userRegistration.getClientEmail(username), bicycleDescription, parkIdentification, username);
            return time;
        } catch (Exception ex) {
            return 0;
        }
    }

   /**
     * Lock a specific escooter at a park.
     *
     * Basic: Lock a specific escooter at a park.
     * Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param escooterDescription Escooter to lock.
     * @param parkLatitudeInDegrees Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees Park Longitude in Decimal degrees.
     * @param username User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     */
    public long lockEscooter(String escooterDescription, double parkLatitudeInDegrees,
            double parkLongitudeInDegrees, String username) {
        if (escooterDescription.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = userRegistration.lockVehicle(escooterDescription, parkLatitudeInDegrees, parkLongitudeInDegrees);
            float cost = calculateCost(username);
            userRegistration.addInvoiceLine(username, Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR), cost);
            mailService.emailBuilder(userRegistration.getClientEmail(username), escooterDescription, parkRegistration.getParkByCoords(parkLatitudeInDegrees, parkLongitudeInDegrees, 0), username);
            return time;
        } catch (Exception ex) {
            return 0;
        }
    }

   /**
     * Lock a specific bicycle at a park.
     *
     * Basic: Lock a specific bicycle at a park.
     * Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param bicycleDescription Bicycle to lock.
     * @param parkLatitudeInDegrees Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees Park Longitude in Decimal degrees.
     * @param username User that requested the unlock.
     * @return The time in milliseconds at which the bicycle was locked.
     */
    public long lockBicycle(String bicycleDescription, double parkLatitudeInDegrees,
            double parkLongitudeInDegrees, String username) {
        if (bicycleDescription.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = userRegistration.lockVehicle(bicycleDescription, parkLatitudeInDegrees, parkLongitudeInDegrees);
            float cost = calculateCost(username);
            userRegistration.addInvoiceLine(username, Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR), cost);
            mailService.emailBuilder(userRegistration.getClientEmail(username), bicycleDescription, parkRegistration.getParkByCoords(parkLatitudeInDegrees, parkLongitudeInDegrees, 0), username);
            return time;
        } catch (Exception ex) {
            return 0;
        }

    }

    /**
     * Calculate the cost that a user should be charged after making a trip.
     * @param username
     * @return 0 if the period of time was less than 1 hour and so it doesn't have to pay nothing,
     * or 1 if the trip passes 1 hour and so calculates the ammount it has to pay.
     */
    public float calculateCost(String username) {
        List<Calendar> list = new ArrayList<>();
        try {
            list = tripRegistration.getUserTripDates(username);
            Calendar udate = list.get(0);
            Calendar ldate = list.get(1);
            long diff = ldate.getTimeInMillis() - udate.getTimeInMillis();
            int diffmin = (int) (diff / (60 * 1000));
            if (diffmin <= 60) {
                return 0;
            } else if (diffmin > 60) {
                diffmin -= 60;
                return diffmin * 0.025f;
            }

        } catch (Exception ex) {
            Logger.getLogger(LockVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
