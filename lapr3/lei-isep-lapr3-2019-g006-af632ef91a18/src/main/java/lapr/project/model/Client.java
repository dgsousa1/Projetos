package lapr.project.model;

public class Client extends User {
    /**
     * The client's email
     */
    private final String email;
    /**
     * The client's height
     */
    private final int height;
    /**
     * The client's weight
     */
    private final int weight;
    /**
     * The client's cycling average speed
     */
    private final float cyclingAverageSpeed;
    /**
     * The client's credit card
     */
    private final String creditCard;
    /**
     * The client's gender
     */
    private final String gender;
    /**
     * The client's points
     */
    private int points;    
    /**
     * Constructor responsible to create a Client
     * @param idUser
     * @param password
     * @param email
     * @param creditCard
     * @param height
     * @param weight
     * @param gender
     * @param cyclingAverageSpeed 
     */
    public Client(String idUser, String password, String email, String creditCard, int height, int weight, String gender, float cyclingAverageSpeed) {
        super(idUser, password);
        if(email.isEmpty() || creditCard.isEmpty() || height == 0 || weight == 0 || gender.isEmpty()){
            throw new IllegalArgumentException("Client data is invalid");
        }        
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.cyclingAverageSpeed = cyclingAverageSpeed;
        this.creditCard = creditCard;
        this.gender = gender;
        this.points = 0;
    }
    /**
     * Method that increments points on a user
     * @param points 
     */
    public void addPoints(int points) {
        this.points += points;
    }
    /**
     * returns the email
     * @return 
     */
    public String getEmail() {
        return email;
    }
    /**
     * returns the height
     * @return 
     */
    public int getHeight() {
        return height;
    }
    /**
     * returns the weight
     * @return 
     */
    public int getWeight() {
        return weight;
    }
    /**
     * returns the cycling average speed
     * @return 
     */
    public float getCyclingAverageSpeed() {
        return cyclingAverageSpeed;
    }
    /**
     * returns the credit card
     * @return 
     */
    public String getCreditCard() {
        return creditCard;
    }
    /**
     * returns the gender
     * @return 
     */
    public String getGender() {
        return gender;
    }
    /**
     * return the points
     * @return 
     */
    public int getPoints(){
        return points;
    }
}
