package lapr.project.utils;

import java.util.List;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Path;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import static lapr.project.utils.GraphArithmetic.getPaths;

/**
 *
 * @author Rafael Crista
 */
public class Calculator {

    private Calculator() {
    }

    public static Double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

    public static Double calcularDistanciaWithElevation(double lat1, double lon1, double lat2, double lon2, double el1, double el2) {
        double distance = calcularDistancia(lat1, lon1, lat2, lon2);
        double height = el1 - el2;
        return Math.sqrt(Math.pow(distance, 2) + Math.pow(height, 2));
    }

    public static Double calculateEnergyForPath(Client client, Path path, Vehicle vehicle) {
        if (client == null || path == null || vehicle == null) {
            return 0.0;
        }
        double g = 9.8;
        int m = client.getWeight() + vehicle.getWeight();
        double h = calcularDistanciaWithElevation(path.getLocationA().getLatitude(), path.getLocationA().getLongitude(),
                path.getLocationB().getLatitude(), path.getLocationB().getLongitude(),
                path.getLocationA().getElevation(), path.getLocationB().getElevation());
        //cos(a)
        double c;
        //sen(a)
        double s;
        if (h == 0) {
            c = 0;
            s = 0;
        } else {
            c = calcularDistancia(path.getLocationA().getLatitude(), path.getLocationA().getLongitude(),
                    path.getLocationB().getLatitude(), path.getLocationB().getLongitude()) / h;

            s = (path.getLocationA().getElevation() - path.getLocationB().getElevation()) / h;
        }
        double k1 = path.getKineticCoefficient();
        double fa = g * m * c * k1;
        double fg = g * m * s;
        double par = 1.9041;
        double d = vehicle.getAerodynamicCoef();
        double a = vehicle.getFrontalArea();
        double vc;
        if (vehicle.getClass().equals(Scooter.class)) {
            vc = 10 / 3.6;
        } else {
            vc = client.getCyclingAverageSpeed() / 3.6;
        }
        double va = path.getWindSpeed();
        //cos(b)
        double b = calculateAngle(h * c, h * s, va * Math.cos(90 - path.getWindDirection()), va * Math.sin(90 - path.getWindDirection()), h, va);
        double fd = par * d * a * Math.pow(vc - va * b, 2) / 2;
        double p = (fa + fg + fd) * vc;
        double t = (h / vc) / 3600;
        //kWh
        return (p * t) / 1000;
    }

    private static double calculateAngle(double ux, double uy, double vx, double vy, double u, double v) {
        if (u == 0 || v == 0) {
            return 0;
        }
        return (ux * vx + uy * vy) / u * v;
    }

    public static double calculateBatteryForGivenDistance(double maxBattery, double power, double actualBattery) {
        double batteryCapacity = maxBattery * (actualBattery / 100);
        return batteryCapacity / power;
    }

    public static double calculatePathDistance(List<Location> path) {
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += calcularDistanciaWithElevation(path.get(i).getLatitude(), path.get(i).getLongitude(), path.get(i + 1).getLatitude(),
                    path.get(i + 1).getLongitude(), path.get(i).getElevation(), path.get(i + 1).getElevation());
        }
        return distance;
    }

    public static double calculatePathEnergy(List<Location> path, Graph<Location, Path> graph, Client client, Vehicle vehicle) {
        double energy = 0;
        List<Path> route = getPaths(path, graph);
        for (Path p : route) {
            energy += calculateEnergyForPath(client, p, vehicle);
        }
        return energy;
    }
}
