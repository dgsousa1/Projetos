package lapr.project.model;

public class Location {
    /**
     * location's longitude
     */
    private final double longitude;
    /**
     * location's latitude
     */
    private final double latitude;
    /**
     * location's elevation
     */
    private final int elevation;
    /**
     * Constructor responsible to create a Location with elevation
     * @param latitude
     * @param longitude
     * @param elevation 
     */
    public Location(double latitude, double longitude, int elevation) {
        if (latitude <= 90 && latitude >= -90) {
            if (longitude <= 180 && longitude >= -180) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.elevation = elevation;
            } else {
                throw new IllegalArgumentException("Longitude out of bounds.");
            }
        } else {
            throw new IllegalArgumentException("Latitude out of bounds.");
        }
    }
    /**
     * Constructor responsible to create a Location without elevation
     * @param latitude
     * @param longitude 
     */
    public Location(double latitude, double longitude) {
        if (latitude <= 90 && latitude >= -90) {
            if (longitude <= 180 && longitude >= -180) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.elevation = 0;
            } else {
                throw new IllegalArgumentException("Longitude out of bounds.");
            }
        } else {
            throw new IllegalArgumentException("Latitude out of bounds.");
        }
    }
    /**
     * returns the longitude
     * @return 
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * returns the latitude
     * @return 
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * returns the elevation
     * @return 
     */
    public int getElevation() {
        return elevation;
    }
    /**
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 17 * hash + this.elevation;
        return hash;
    }
    /**
     * returns boolean value
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        return true;
    }
    
    
}
