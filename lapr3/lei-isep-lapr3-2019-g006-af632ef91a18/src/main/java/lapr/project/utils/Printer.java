/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.InvoiceTripReport;
import lapr.project.data.PointsTripReport;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;

/**
 *
 * @author Nuno
 */
public class Printer {

    private Printer() {
    }

    private static final String EXCEPTIONMSG = "No File";

    public static void printPathEletric(List<Location> locations, String outputFile, Client client, Vehicle vehicle, double totalEnergy) throws IOException {
        if (!locations.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                int i = 1;
                writer.write("Path " + i + "\n");
                i++;
                writer.write("total_distance:" + Math.round(Calculator.calculatePathDistance(locations)) + "\n");
                writer.write("total_energy:" + Math.round(totalEnergy * 100) / 100 + "\n");
                writer.write("elevation:" + (locations.get(0).getElevation() - locations.get(locations.size() - 1).getElevation()) + "\n");
                for (Location location : locations) {
                    writer.write(location.getLatitude() + ";" + location.getLongitude() + "\n");
                }
            } catch (IOException e) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
                throw new IOException(EXCEPTIONMSG);
            }
        }
    }

    public static void printScooters(List<Scooter> usableScooters, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("escooter description;type;actual battery capacity");
            for (Scooter s : usableScooters) {
                writer.newLine();
                writer.write(s.getIdVehicle() + ";" + s.getType() + ";" + s.getActualBatteryCapacity());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException(EXCEPTIONMSG);
        }
    }

    public static void printPark(Map<Double, Park> nearestParks, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));) {
            writer.write("latitude;longitude;distance in meters");
            for (Map.Entry<Double, Park> entry : nearestParks.entrySet()) {
                writer.newLine();
                Park p = entry.getValue();
                int distance = (int) Math.round(entry.getKey());
                writer.write(p.getLocation().getLatitude() + ";" + p.getLocation().getLongitude() + ";" + distance);
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException(EXCEPTIONMSG);
        }
    }

    public static void printScooter(Scooter s, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("escooter description;type;actual battery capacity");
            writer.newLine();
            writer.write(s.getIdVehicle() + ";" + s.getType() + ";" + s.getActualBatteryCapacity());
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException(EXCEPTIONMSG);
        }
    }

    public static void printChargingTimeScooters(Map<Scooter, Double> scooters, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("escooter description;type;actual battery capacity;time to finish charge in seconds");
            for (Map.Entry<Scooter, Double> entry : scooters.entrySet()) {
                writer.newLine();
                Scooter s = entry.getKey();
                double chargingTime = entry.getValue();
                writer.write(s.getIdVehicle() + ";" + s.getType() + ";" + s.getActualBatteryCapacity() + ";" + chargingTime);
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException(EXCEPTIONMSG);
        }
    }

    public static void printBicycles(List<Bicycle> usableBicycles, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("bicycle description;wheel size");
            for (Bicycle s : usableBicycles) {
                writer.newLine();
                writer.write(s.getIdVehicle() + ";" + s.getWheelSize());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException(EXCEPTIONMSG);
        }
    }

    public static void printInvoice(String username, int previousMonthPoints, int pointsEarnedThisMonth, int discountedPoints, int actualPoints, float currentCost, List<InvoiceTripReport> list, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(username + "\n");
            writer.write("Previous points:" + previousMonthPoints + "\n");
            writer.write("Earned points:" + pointsEarnedThisMonth + "\n");
            writer.write("Discounted points:" + discountedPoints + "\n");
            writer.write("Actual points:" + actualPoints + "\n");
            writer.write("Charged Value:" + currentCost);
            for (InvoiceTripReport s : list) {
                long diffInSeconds = (s.getLockingDate().getTime() - s.getUnlockingDate().getTime()) / 1000;
                writer.newLine();
                writer.write(s.getIdVehicle() + ";" + s.getUnlockingDate().getTime() + ";"
                        + s.getLockingDate().getTime() + ";" + s.getStartLatitude() + ";" + s.getStartLongitude()
                        + ";" + s.getEndLatitude() + ";" + s.getEndLongitude() + ";" + diffInSeconds + ";" + s.getCost());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("No file");
        }
    }

    public static void printPathShortestDistance(List<Location> locations, String outputFile, double totalDistance) throws IOException {
        if (!locations.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("Path " + 1 + "\n");
                writer.write("total_distance:" + Math.round(totalDistance) + "\n");
                writer.write("total_energy:" + 0 + "\n");
                writer.write("elevation:" + (locations.get(0).getElevation() - locations.get(locations.size() - 1).getElevation()) + "\n");
                for (Location location : locations) {
                    writer.write(location.getLatitude() + ";" + location.getLongitude() + "\n");
                }
            } catch (IOException e) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
                throw new IOException(EXCEPTIONMSG);
            }
        }
    }

    public static void printBalance(List<InvoiceTripReport> list, String outputPath) throws IOException {
        Collections.sort(list, ascendingInvoiceUnlockTime);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("vehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park longitude;destination park latitude;destination park longitude;total time spent in seconds;charged value");
            for (InvoiceTripReport s : list) {
                long diffInSeconds = (s.getLockingDate().getTime() - s.getUnlockingDate().getTime()) / 1000;
                writer.newLine();
                writer.write(s.getIdVehicle() + ";" + s.getUnlockingDate().getTime() + ";"
                        + s.getLockingDate().getTime() + ";" + s.getStartLatitude() + ";" + s.getStartLongitude()
                        + ";" + s.getEndLatitude() + ";" + s.getEndLongitude() + ";" + diffInSeconds + ";" + s.getCost());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("No file");
        }
    }

    private static final Comparator<InvoiceTripReport> ascendingInvoiceUnlockTime = (InvoiceTripReport invoice1, InvoiceTripReport invoice2) -> {
        return invoice1.getUnlockingDate().compareTo(invoice2.getUnlockingDate());
    };

    public static void printPoints(List<PointsTripReport> list, String outputPath) throws IOException {
        Collections.sort(list, ascendingPointsUnlockTime);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("vehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park longitude;origin park elevation;destination park latitude;destination park longitude;destination park elevation;elevation difference;points");
            for (PointsTripReport s : list) {
                int diffElevation = Math.abs(s.getEndElevation()-s.getStartElevation());
                writer.newLine();
                writer.write(s.getIdVehicle() + ";" + s.getUnlockingDate().getTime() + ";"
                        + s.getLockingDate().getTime() + ";" + s.getStartLatitude() + ";" + s.getStartLongitude()
                        + ";" + s.getStartElevation() + ";" + s.getEndLatitude() + ";" + s.getEndLongitude()
                        + ";" + s.getEndElevation() + ";" + diffElevation + ";" + s.getPoints());
            }
        } catch (IOException e) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("No file");
        }
    }

    private static final Comparator<PointsTripReport> ascendingPointsUnlockTime = (PointsTripReport invoice1, PointsTripReport invoice2) -> {
        return invoice1.getUnlockingDate().compareTo(invoice2.getUnlockingDate());
    };
}
