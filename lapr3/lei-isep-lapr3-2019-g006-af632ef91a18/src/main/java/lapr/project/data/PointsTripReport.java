package lapr.project.data;

import java.util.Date;

/**
 *
 *
 */
public class PointsTripReport {

    private int idTrip;
    private String idVehicle;
    private String idClient;
    private double startLatitude;
    private double startLongitude;
    private int startElevation;
    private double endLatitude;
    private double endLongitude;
    private int endElevation;
    private Date unlockingDate;
    private Date lockingDate;
    private int points;

    public PointsTripReport(int idTrip, String idVehicle, String idClient, double startLatitude, double startLongitude, int startElevation, double endLatitude, double endLongitude, int endElevation, Date unlockingDate, Date lockingDate, int points) {
        this.idTrip = idTrip;
        this.idVehicle = idVehicle;
        this.idClient = idClient;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.startElevation = startElevation;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.endElevation = endElevation;
        this.unlockingDate = new Date(unlockingDate.getTime());
        this.lockingDate = new Date(lockingDate.getTime());
        this.points = points;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public int getStartElevation() {
        return startElevation;
    }

    public void setStartElevation(int startElevation) {
        this.startElevation = startElevation;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public int getEndElevation() {
        return endElevation;
    }

    public void setEndElevation(int endElevation) {
        this.endElevation = endElevation;
    }

    public Date getUnlockingDate() {
        return new Date(this.unlockingDate.getTime());
    }

    public void setUnlockingDate(Date unlockingDate) {
        this.unlockingDate = new Date(this.unlockingDate.getTime());
    }

    public Date getLockingDate() {
        return new Date(this.lockingDate.getTime());
    }

    public void setLockingDate(Date lockingDate) {
        this.lockingDate = new Date(this.lockingDate.getTime());
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
