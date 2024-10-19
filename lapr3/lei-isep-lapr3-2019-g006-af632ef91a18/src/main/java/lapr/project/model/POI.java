package lapr.project.model;

public class POI {
    /**
     * POI's description
     */
    private String description;
    /**
     * POI's location
     */
    private Location location;
    /**
     * Constructor responsible to create a POI
     * @param description
     * @param location 
     */
    public POI(String description, Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Path data is invalid");
        }
        this.description = description;
        this.location = location;
    }
    /**
     * returns the description
     * @return 
     */
    public String getDescription() {
        return description;
    }
    /**
     * modifies the description
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * returns the location
     * @return 
     */
    public Location getLocation() {
        return location;
    }
    /**
     * modifies the location
     * @param location 
     */
    public void setLocation(Location location) {
        this.location = location;
    }

}
