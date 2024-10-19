package lapr.project.model;

public abstract class User {
    /**
     * user's id
     */
    String idUser;
    /**
     * user's password
     */
    private final String password;
    /**
     * Constructor responsible to create a User
     * @param idUser
     * @param password 
     */
    public User(String idUser, String password) {
        if(idUser.isEmpty() || password.isEmpty()){
            throw new IllegalArgumentException("User data is invalid");
        }        
        this.idUser = idUser;
        this.password = password;
    }
    /**
     * returns user's id
     * @return 
     */
    public String getIdUser() {
        return idUser;
    }
    /**
     * returns user's password
     * @return 
     */
    public String getPassword() {
        return password;
    }
}
