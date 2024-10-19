package lapr.project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import lapr.project.model.TripRequest;
import lapr.project.utils.Printer;

public class UnlockVehicleController {

    /**
     * Trip manager.
     */
    private final TripRegistration tr;
    
    /**
     * Vehicle manager.
     */
    private final VehicleRegistration vr;
    
    /**
     * Park manager.
     */
    private final ParkRegistration pr;
    
    /**
     * User manager.
     */
    private final UserRegistration ur;
    public static final String PAM_ERROR = "One or more parameters are empty";

    /**
     * Builder responsible for getting the unlock vehicle being used.
     * @param tr
     * @param vr
     * @param ur
     * @param pr 
     */
    public UnlockVehicleController(TripRegistration tr, VehicleRegistration vr, UserRegistration ur, ParkRegistration pr) {
        this.tr = tr;
        this.vr = vr;
        this.ur = ur;
        this.pr = pr;
    }

      /**
     * Unlocks a specific bicycle.
     *
     * @param username User that requested the unlock.
     * @param bicycleDescription Bicycle description to unlock.
     * @return The time in milliseconds at which it was unlocked.
     */
    public long unlockBicycle(String bicycleDescription, String username) {
        if (bicycleDescription.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = ur.unlockVehicleParkID(bicycleDescription);
            Bicycle b = vr.getBicycle(bicycleDescription);
            Client c = ur.getClient(username);
            TripRequest trip = new TripRequest(b, b.getLocation(), c, LocalDateTime.MIN);

            tr.saveTrip(trip);
            return time;
        } catch (Exception ex) {
            return 0;
        }
    }

   /**
     * Unlocks a specific escooter.
     *
     * @param username User that requested the unlock.
     * @param escooterDescription Escooter description to unlock.
     * @return The time in milliseconds at which it was unlocked.
     */
    public long unlockScooter(String escooterDescription, String username) {
        if (escooterDescription.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException(PAM_ERROR);
        }
        try {
            long time = ur.unlockVehicleParkID(escooterDescription);
            Scooter s = vr.getScooter(escooterDescription);
            Client c = ur.getClient(username);
            TripRequest trip = new TripRequest(s, s.getLocation(), c, LocalDateTime.MIN);

            tr.saveTrip(trip);
            return time;
        } catch (Exception ex) {
            return 0;
        }

    }

   /**
     * Unlocks any escooter at one park. It should unlock the one
     * with higher battery capacity.
     *
     * @param parkIdentification Park Identification where to unlock escooter.
     * @param username User that requested the unlock.
     * @param outputFileName Write the unlocked vehicle information to a file,
     *                       according to file output/escooters.csv.
     * @return The time in milliseconds at which it was unlocked.
     */
    public long unlockAnyEscooterAtPark(String parkIdentification, String username, String outputFileName) {
        String idScooter;
        Scooter s;
        try {
            idScooter = ur.unlockAnyEscootereAtPark(parkIdentification);
            //Park p = pr.getPark(parkIdentification);
            s = vr.getScooter(idScooter);

            Client c = ur.getClient(username);
            TripRequest trip = new TripRequest(s, s.getLocation(), c, LocalDateTime.MIN);

            tr.saveTrip(trip);
        } catch (Exception ex) {
            return 0;
        }
        try {
            Printer.printScooter(s, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return System.currentTimeMillis();
    }

  
    /**
     * Unlocks any escooter at one park that allows travelling to the
     * destination.
     *
     * @param parkIdentification Park Identification where to unlock escooter.
     * @param username User that requested the unlock.
     * @param destinyLatitudeInDegrees Destiny latitude in Decimal Degrees.
     * @param destinyLongitudeInDegrees Destiny longitude in Decimal Degrees.
     * @param outputFileName Write the unlocked vehicle information to a file,
     *                       according to file output/escooters.csv.
     * @return The time in milliseconds at which it was unlocked.
     */
    public long unlockAnyEscootereAtParkForDestination(String parkIdentification, String username,
            double destinyLatitudeInDegrees, double destinyLongitudeInDegrees, String outputFileName) {
        String idScooter;
        Scooter s;
        try {
            idScooter = ur.unlockAnyEscootereAtPark(parkIdentification);
            //Park p = pr.getPark(pr.getParkByCoords(destinyLatitudeInDegrees, destinyLongitudeInDegrees, 0));
            s = vr.getScooter(idScooter);

            Client c = ur.getClient(username);
            TripRequest trip = new TripRequest(s, s.getLocation(), c, LocalDateTime.MIN);

            tr.saveTrip(trip);
        } catch (Exception ex) {
            return 0;
        }
        try {
            Printer.printScooter(s, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return System.currentTimeMillis();
    }
}
