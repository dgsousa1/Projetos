package lapr.project.model;

public class Scooter extends Vehicle {
    /**
     * scooter's type
     */
    private final String type;
    /**
     * scooter's max battery capacity
     */
    private final float maxBatteryCapacity;
    /**
     * scooter's actual battery capacity
     */
    private final float actualBatteryCapacity;
    /**
     * scooter's power
     */
    private final float power;
    /**
     * Constructor responsible to create a Scooter
     * @param type
     * @param maxBatteryCapacity
     * @param actualBatteryCapacity
     * @param idVehicle
     * @param weight
     * @param location
     * @param aerodynamicCoef
     * @param frontalArea
     * @param power 
     */
    public Scooter(String type, float maxBatteryCapacity, float actualBatteryCapacity, String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea, float power) {
        super(idVehicle, weight, location, aerodynamicCoef, frontalArea);
        if(!type.equals("city") && !type.equals("off-road") || maxBatteryCapacity <= 0 || actualBatteryCapacity < 0 || power < 0){
            throw new IllegalArgumentException("Scooter data is invalid");
        }
        this.type = type;
        this.maxBatteryCapacity = maxBatteryCapacity;
        this.actualBatteryCapacity = actualBatteryCapacity;
        this.power = power;
    }
    /**
     * returns the scooter's type
     * @return 
     */
    public String getType() {
        return type;
    }
    /**
     * returns the scooter's max battery capacity
     * @return 
     */
    public float getMaxBatteryCapacity() {
        return maxBatteryCapacity;
    }
    /**
     * returns the scooter's actual battery capacity
     * @return 
     */
    public float getActualBatteryCapacity() {
        return actualBatteryCapacity;
    }
    /**
     * returns the scooter's power 
     * @return 
     */
    public float getPower(){
        return power;
    }
}
