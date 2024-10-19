package lapr.project.data;

import java.util.Date;

/**
 *
 *  Not a model class, used to retrieve data from DB for the Invoice
 */
public class InvoiceTripReport {
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
    private float cost;

    /**
     * Builder responsible for getting the invoice trip report being used.
     * @param idTrip
     * @param idVehicle
     * @param idClient
     * @param startLatitude
     * @param startLongitude
     * @param startElevation
     * @param endLatitude
     * @param endLongitude
     * @param endElevation
     * @param unlockingDate
     * @param lockingDate
     * @param cost 
     */
    public InvoiceTripReport(int idTrip, String idVehicle, String idClient, double startLatitude, double startLongitude, int startElevation, double endLatitude, double endLongitude, int endElevation, Date unlockingDate, Date lockingDate, float cost) {
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
        this.cost = cost;
    }

    /**
     * Returns the id trip.
     * @return idTrip.
     */
    public int getIdTrip() {
        return idTrip;
    }

    /**
     * Modifies the id trip.
     * @param idTrip 
     */
    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    /**
     * Returns the id vehicle.
     * @return idVehicle
     */
    public String getIdVehicle() {
        return idVehicle;
    }

    /**
     * Modifies the id vehicle.
     * @param idVehicle 
     */
    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    /**
     * Returns the id client.
     * @return idClient
     */
    public String getIdClient() {
        return idClient;
    }

    /**
     * Modifies the id client.
     * @param idClient 
     */
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    /**
     * Returns the start latitude.
     * @return startLatitude
     */
    public double getStartLatitude() {
        return startLatitude;
    }

    /**
     * Modifies the start latitude.
     * @param startLatitude 
     */
    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    /**
     * Returns the start longitude.
     * @return startLongitude
     */
    public double getStartLongitude() {
        return startLongitude;
    }

    /**
     * Modifies the start longitude.
     * @param startLongitude 
     */
    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    /**
     * Returns the start elevation.
     * @return startElevation
     */
    public int getStartElevation() {
        return startElevation;
    }

    /**
     * Modifies the start elevation.
     * @param startElevation 
     */
    public void setStartElevation(int startElevation) {
        this.startElevation = startElevation;
    }

    /**
     * Returns the end latitude.
     * @return endLatitude
     */
    public double getEndLatitude() {
        return endLatitude;
    }

    /**
     * Modifies the end latitude.
     * @param endLatitude 
     */
    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    /**
     * Returns the end longitude.
     * @return endLongitude
     */
    public double getEndLongitude() {
        return endLongitude;
    }

    /**
     * Modifies the end longitude.
     * @param endLongitude 
     */
    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    /**
     * Returns the end elevation.
     * @return endElevation
     */
    public int getEndElevation() {
        return endElevation;
    }

    /**
     * Modifies the end elevation.
     * @param endElevation 
     */
    public void setEndElevation(int endElevation) {
        this.endElevation = endElevation;
    }

    /**
     * Returns the unlocking date.
     * @return unlockingDate
     */
    public Date getUnlockingDate() {
        return new Date(this.unlockingDate.getTime());
    }

    /**
     * Modifies the unlocking date.
     * @param unlockingDate 
     */
    public void setUnlockingDate(Date unlockingDate) {
        this.unlockingDate = new Date(this.unlockingDate.getTime());
    }

    /**
     * Returns the locking date.
     * @return lockingDate
     */
    public Date getLockingDate() {
        return new Date(this.lockingDate.getTime());
    }

    /**
     * Modifies the locking date.
     * @param lockingDate 
     */
    public void setLockingDate(Date lockingDate) {
        this.lockingDate = new Date(this.lockingDate.getTime());
    }

    /**
     * Returns the cost.
     * @return cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * Modifies the cost.
     * @param cost 
     */
    public void setCost(float cost) {
        this.cost = cost;
    }
    
    
    
}
