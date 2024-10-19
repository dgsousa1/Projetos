package lapr.project.model;

import java.time.LocalDateTime;

public class TripRequest {
    /**
     * vehicle
     */
    private final Vehicle vehicle;
    /**
     * origin location
     */
    private final Location origin;
    /**
     * destination location
     */
    private Location destination;
    /**
     * client
     */
    private final Client client;
    /**
     * initial date
     */
    private final LocalDateTime initialDate;
    /**
     * final date
     */
    private LocalDateTime finalDate;
    /**
     * Constructor responsible to create a TripRequest
     * @param vehicle
     * @param origin
     * @param client
     * @param initialDate 
     */
    public TripRequest(Vehicle vehicle, Location origin, Client client, LocalDateTime initialDate) {
        if (vehicle == null || origin == null || client == null || initialDate == null) {
            throw new IllegalArgumentException("TripRequest data is invalid");
        }
        this.vehicle = vehicle;
        this.origin = origin;
        this.client = client;
        this.initialDate = initialDate;
    }
    /**
     * returns vehicle
     * @return 
     */
    public Vehicle getVehicle() {
        return vehicle;
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
     * returns client
     * @return 
     */
    public Client getClient() {
        return client;
    }
    /**
     * returns inital date
     * @return 
     */
    public LocalDateTime getInitialDate() {
        return initialDate;
    }
    /**
     * returns final date
     * @return 
     */
    public LocalDateTime getFinalDate() {
        return finalDate;
    }
    /**
     * modifies destination location
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
     * modifies final date
     * @param finalDate 
     */
    public void setFinalDate(LocalDateTime finalDate) {
        if (finalDate != null) {
            this.finalDate = finalDate;
        } else {
            throw new IllegalArgumentException("Invalid final date");
        }
    }
}
