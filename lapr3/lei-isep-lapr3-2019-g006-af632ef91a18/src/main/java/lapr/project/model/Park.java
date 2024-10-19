package lapr.project.model;

public class Park {
    /**
     * Park's id
     */
    private final String idPark;
    /**
     * Park's location
     */
    private final Location location;
    /**
     * Park's description
     */
    private final String description;
    /**
     * Park's max number of bicycle slots
     */
    private final int maxBike;
    /**
     * Park's max number of bicycle slots
     */
    private final int maxScooter;
    /**
     * Park's input voltage
     */
    private final float inputVoltage;
    /**
     * Park's input current
     */
    private final float inputCurrent;
    /**
     * Constructor responsible to create a Park
     * @param idPark
     * @param location
     * @param description
     * @param maxBike
     * @param maxScooter
     * @param inputVoltage
     * @param inputCurrent 
     */
    public Park(String idPark, Location location, String description,  int maxBike, int maxScooter,float inputVoltage, float inputCurrent) {
         if(idPark.isEmpty() || location == null || description.isEmpty()  || maxBike == 0 || maxScooter == 0 || inputVoltage == 0 || inputCurrent == 0){
            throw new IllegalArgumentException("Park data is invalid");
        }
        this.idPark = idPark;
        this.location = location;
        this.description = description;        
        this.maxBike = maxBike;
        this.maxScooter = maxScooter;
        this.inputVoltage = inputVoltage;
        this.inputCurrent = inputCurrent;
    }
    /**
     * returns park's id
     * @return 
     */
    public String getIdPark() {
        return idPark;
    }
    /**
     * returns the park's location
     * @return 
     */
    public Location getLocation() {
        return location;
    }
    /**
     * returns the park's description
     * @return 
     */
    public String getDescription() {
        return description;
    }
    /**
     * returns the park's max number of bicycle slots
     * @return 
     */
    public int getMaxBike() {
        return maxBike;
    }
    /**
     * returns the park's max number of scooter slots
     * @return 
     */
    public int getMaxScooter() {
        return maxScooter;
    }
    /**
     * returns the park's input voltage
     * @return 
     */
    public float getInputVoltage() {
        return inputVoltage;
    }
    /**
     * returns the park's input current
     * @return 
     */
    public float getInputCurrent() {
        return inputCurrent;
    }
}
