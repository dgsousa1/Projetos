package lapr.project.model;

public class Path {
    /**
     * path's location A
     */
    private Location locationA;
    /**
     * path's location B
     */
    private Location locationB;
    /**
     * path's kinetic coefficient
     */
    private float kineticCoefficient;
    /**
     * path's wind direction
     */
    private float windDirection;
    /**
     * path's wind speed
     */
    private float windSpeed;
    /**
     * Constructor responsible to create a Path
     * @param locationA
     * @param locationB
     * @param kineticCoefficient
     * @param windDirection
     * @param windSpeed 
     */
    public Path(Location locationA, Location locationB, float kineticCoefficient, float windDirection, float windSpeed) {
        if (locationA == null || locationB == null) {
            throw new IllegalArgumentException("Path data is invalid");
        }
        this.locationA = locationA;
        this.locationB = locationB;
        this.kineticCoefficient = kineticCoefficient;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }
    /**
     * returns the path's location A
     * @return 
     */
    public Location getLocationA() {
        return locationA;
    }
    /**
     * modifies the path's location A
     * @return 
     */
    public void setLocationA(Location locationA) {
        this.locationA = locationA;
    }
    /**
     * returns the path's location B
     * @return 
     */
    public Location getLocationB() {
        return locationB;
    }
    /**
     * modifies the path's location B
     * @return 
     */
    public void setLocationB(Location locationB) {
        this.locationB = locationB;
    }
    /**
     * returns the path's kinetic coefficient
     * @return 
     */
    public float getKineticCoefficient() {
        return kineticCoefficient;
    }
    /**
     * modifies the path's kinetic coefficient
     * @return 
     */
    public void setKineticCoefficient(float kineticCoefficient) {
        this.kineticCoefficient = kineticCoefficient;
    }
    /**
     * returns the path's wind direction
     * @return 
     */
    public float getWindDirection() {
        return windDirection;
    }
    
    /**
     * modifies the path's wind direction
     * @return 
     */
    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }
    /**
     * returns the path's wind speed
     * @return 
     */
    public float getWindSpeed() {
        return windSpeed;
    }
    /**
     * modifies the path's wind speed
     * @return 
     */
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

}
