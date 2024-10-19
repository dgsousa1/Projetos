package lapr.project.model;

public class Bicycle extends Vehicle {
    /**
     * bicycle's wheel size
     */
    private final int wheelSize;
    
    /**
     * Constructor responsible to create a Bicycle
     * @param wheelSize
     * @param idVehicle
     * @param weight
     * @param location
     * @param aerodynamicCoef
     * @param frontalArea 
     */
    public Bicycle(int wheelSize, String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea) {
        super(idVehicle, weight, location, aerodynamicCoef, frontalArea);
        if(wheelSize == 0){
            throw new IllegalArgumentException("Bicycle data is invalid");
        }
        this.wheelSize = wheelSize;
    }
    
    /**
     * returns the wheel size
     * @return 
     */
    public int getWheelSize() {
        return wheelSize;
    }
}
