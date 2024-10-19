package lapr.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Location;
import lapr.project.model.Scooter;
import lapr.project.utils.Calculator;

/**
 *
 *
 */
public class VehicleController {

    /**
     * Vehicle manager.
     */
    VehicleRegistration registration;

    /**
     * Builder responsible for getting the vehicle being used.
     * @param registration 
     */
    public VehicleController(VehicleRegistration registration) {
        this.registration = registration;
    }

    /**
     * Add Bicycles to the system.
     *
     * Basic: Add one bicycle to one park.
     * Intermediate: Add several bicycles to one park.
     * Advanced: Add several bicycles to several parks transactionally.
     *
     * @param s
     * @return Number of added bicycles.
     */
    public int addBicycle(String s) {
        List<Bicycle> list = new LinkedList<>();
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            br.lines().forEach(line -> {
                String[] param = line.split(";");
                String idVehicle = param[0].trim();
                int weight = Integer.parseInt(param[1].trim());
                double latitude = Double.parseDouble(param[2].trim());
                double longitude = Double.parseDouble(param[3].trim());
                Location location = new Location(latitude, longitude);
                float aerodynamicCoef = Float.parseFloat(param[4].trim());
                float frontalArea = Float.parseFloat(param[5].trim());
                int wheelSize = Integer.parseInt(param[6].trim());
                Bicycle bike = new Bicycle(wheelSize, idVehicle, weight, location, aerodynamicCoef, frontalArea);
                list.add(bike);
            });

        } catch (IOException ex) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            registration.addBicycleBatch(list);
        } catch (Exception ex) {
            return 0;
        }
        return list.size();
    }

/**
     * Add Escooters to the system.
     *
     * Basic: Add one Escooter to one park.
     * Intermediate: Add several Escooters to one park.
     * Advanced: Add several Escooters to several parks transactionally.
     *
     * @param s
     * @return Number of added escooters.
     */
    public int addScooter(String s) {
        List<Scooter> list = new LinkedList<>();
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            br.lines().forEach(line -> {
                String[] param = line.split(";");
                String idVehicle = param[0].trim();
                int weight = Integer.parseInt(param[1].trim());
                String type = param[2].trim();
                double latitude = Double.parseDouble(param[3].trim());
                double longitude = Double.parseDouble(param[4].trim());
                Location location = new Location(latitude, longitude);
                float maxBatteryCapacity = Float.parseFloat(param[5].trim());
                float actualBatteryCapacity = Float.parseFloat(param[6].trim());
                float aerodynamicCoef = Float.parseFloat(param[7].trim());
                float frontalArea = Float.parseFloat(param[8].trim());
                float power = Float.parseFloat(param[9].trim());
                Scooter scooter = new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location, aerodynamicCoef, frontalArea, power);
                list.add(scooter);
            });
        } catch (IOException ex) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            registration.addScooterBatch(list);
        } catch (Exception ex) {
            return 0;
        }
        return list.size();
    }

    /**
     * Remove a bicycle from the system.
     * @param id
     * @return The number of removed bicycles.
     */
    public int removeBicycle(String id) {
        try {
            registration.removeBicycle(id);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * Remove a scooter from the system.
     * @param id
     * @return The number of removed scooters.
     */
    public int removeScooter(String id) {
        try {
            registration.removeScooter(id);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * Updates the information about a bicycle so it can be manageabel.
     * @param s
     * @return 0 if zero things were changed in the bicycle and so nothing happens,
     * or 1 if something was changed and so the bicycle is updated.
     */
    public int updateBicycle(String s) {
        String[] param = s.split(";");
        String idVehicle = param[0].trim();
        int weight = Integer.parseInt(param[1].trim());
        double latitude = Double.parseDouble(param[2].trim());
        double longitude = Double.parseDouble(param[3].trim());
        Location location = new Location(latitude, longitude);
        float aerodynamicCoef = Float.parseFloat(param[4].trim());
        float frontalArea = Float.parseFloat(param[5].trim());
        int wheelSize = Integer.parseInt(param[6].trim());
        Bicycle bike = new Bicycle(wheelSize, idVehicle, weight, location, aerodynamicCoef, frontalArea);

        try {
            registration.updateBicycle(bike);
        } catch (Exception ex) {
            return 0;
        }
        return 1;
    }

    /**
     * Updates the information about a scooter so it can be manageabel.
     * @param s
     * @return 0 if zero things were changed in the scooter and so nothing happens,
     * or 1 if something was changed and so the scooter is updated.
     */
    public int updateScooter(String s) {
        String[] param = s.split(";");
        String idVehicle = param[0].trim();
        int weight = Integer.parseInt(param[1].trim());
        String type = param[2].trim();
        double latitude = Double.parseDouble(param[3].trim());
        double longitude = Double.parseDouble(param[4].trim());
        Location location = new Location(latitude, longitude);
        float maxBatteryCapacity = Float.parseFloat(param[5].trim());
        float actualBatteryCapacity = Float.parseFloat(param[6].trim());
        float aerodynamicCoef = Float.parseFloat(param[7].trim());
        float frontalArea = Float.parseFloat(param[8].trim());
        float power = Float.parseFloat(param[9].trim());
        Scooter scooter = new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location, aerodynamicCoef, frontalArea, power);
        try {
            registration.updateScooter(scooter);
        } catch (Exception ex) {
            return 0;
        }
        return 1;
    }

    /**
     * It gets the report about the electric capacity.
     * @param numberOfKm
     * @return scootersAvailable
     */
    public List<Scooter> getReportAboutEletricCapacity(double numberOfKm) {
        List<Scooter> allScooters;
        List<Scooter> scootersAvailable;
        try {
            allScooters = registration.getAllScooters();
        }
        catch(Exception ex){
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
            
        scootersAvailable = new ArrayList<>();
        //assuming that speed is constant and equals to 10km/h
        int speed = 10;
        double realTime = numberOfKm * 1 / speed;

        for(Scooter s : allScooters) {
            double availableTime = Calculator.calculateBatteryForGivenDistance(s.getMaxBatteryCapacity(), s.getPower(), s.getActualBatteryCapacity());
            if (availableTime >= realTime) {
                scootersAvailable.add(s);
            }
        }
        return scootersAvailable;
    }
}
