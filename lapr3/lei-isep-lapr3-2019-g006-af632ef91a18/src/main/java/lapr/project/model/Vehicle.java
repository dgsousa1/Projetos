package lapr.project.model;

public abstract class Vehicle {
    /**
     * vehicle's id
     */
    private final String idVehicle;
    /**
     * vehicle's weight
     */
    private final int weight;
    /**
     * vehicle's location
     */
    private final Location location;
    /**
     * vehicle's aerodynamic coefficient
     */
    private final float aerodynamicCoef;
    /**
     * vehicle's frontal area
     */
    private final float frontalArea;
    /**
     * Constructor responsible to create a Vehicle
     * @param idVehicle
     * @param weight
     * @param location
     * @param aerodynamicCoef
     * @param frontalArea 
     */
    public Vehicle(String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea) {
        if(idVehicle.isEmpty() || weight == 0 || location == null || aerodynamicCoef == 0 || frontalArea == 0){
            throw new IllegalArgumentException("Vehicle data is invalid");
        }
        this.idVehicle = idVehicle;
        this.weight = weight;
        this.location = location;
        this.aerodynamicCoef = aerodynamicCoef;
        this.frontalArea = frontalArea;
    }
    /**
     * returns vehicle's id
     * @return 
     */
    public String getIdVehicle() {
        return idVehicle;
    }
    /**
     * returns vehicle's weight
     * @return 
     */
    public int getWeight() {
        return weight;
    }
    /**
     * returns vehicle's location
     * @return 
     */
    public Location getLocation() {
        return location;
    }
    /**
     * returns vehicle's aerodynamic coefficient
     * @return 
     */
    public float getAerodynamicCoef() {
        return aerodynamicCoef;
    }
    /**
     * returns vehicle's frontal area
     * @return 
     */
    public float getFrontalArea() {
        return frontalArea;
    }

}
