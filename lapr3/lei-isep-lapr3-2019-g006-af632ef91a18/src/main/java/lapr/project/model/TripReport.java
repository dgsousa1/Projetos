package lapr.project.model;

public class TripReport {
    /**
     * vehicle's id for the trip report
     */
    private final String idVehicle;
    /**
     * client's id for the trip report
     */
    private final String idClient;
    /**
     * client's origin location for the trip report
     */
    private final Location origin;
    /**
     * client's destination location for the trip report
     */
    private Location destination;
    /**
     * client's initial date for the trip report
     */
    private final String initialDate;
    /**
     * client's final date for the trip report
     */
    private String finalDate;
    /**
     * Constructor responsible to create a TripReport
     * @param idVehicle
     * @param idClient
     * @param origin
     * @param destination
     * @param initialDate
     * @param finalDate 
     */
    public TripReport(String idVehicle, String idClient, Location origin, Location destination, String initialDate, String finalDate) {
        this.idVehicle = idVehicle;
        this.idClient = idClient;
        this.origin = origin;
        this.destination = destination;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }
    /**
     * returns the vehicle's id
     * @return 
     */
    public String getIdVehicle() {
        return idVehicle;
    }
    /**
     * returns the client's id
     * @return 
     */
    public String getIdClient() {
        return idClient;
    }
    /**
     * returns origin location
     * @return 
     */
    public Location getOrigin() {
        return origin;
    }
    /**
     * returns destination location
     * @return 
     */
    public Location getDestination() {
        return destination;
    }
    /**
     * returns initial date
     * @return 
     */
    public String getInitialDate() {
        return initialDate;
    }
    /**
     * returns final date
     * @return 
     */
    public String getFinalDate() {
        return finalDate;
    }
    /**
     * modifies the destination location
     * @param destination 
     */
    public void setDestination(Location destination) {
        if (destination != null) {
            this.destination = destination;
        } else {
            throw new IllegalArgumentException("Invalid destination");
        }
    }
    /**
     * modifies the final date
     * @param finalDate 
     */
    public void setFinalDate(String finalDate) {
        if (finalDate != null) {
            this.finalDate = finalDate;
        } else {
            throw new IllegalArgumentException("Invalid final date");
        }
    }

}
